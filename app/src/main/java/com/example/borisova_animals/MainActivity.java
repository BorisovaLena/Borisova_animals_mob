package com.example.borisova_animals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Adapter pAdapter;
    private List<Animal> listProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView ivProducts = findViewById(R.id.BD_Animals);
        pAdapter = new Adapter(MainActivity.this, listProduct);
        ivProducts.setAdapter(pAdapter);
        new GetAnim().execute();
    }

    private class GetAnim extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL("http://10.0.2.2:/api/Animals");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    result.append(str);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Animal tempAnimal = new Animal(
                            productJson.getInt("ID"),
                            productJson.getString("Title"),
                            productJson.getString("Kingdom"),
                            productJson.getString("Type"),
                            productJson.getString("Class"),
                            productJson.getString("Detachment"),
                            productJson.getString("Family"),
                            productJson.getString("Image")
                    );
                    listProduct.add(tempAnimal);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }

    }

}