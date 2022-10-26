package com.example.borisova_animals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class add_Animal extends AppCompatActivity {

    EditText Title;
    EditText Kingdom;
    EditText Type;
    EditText Class;
    EditText Detachment;
    EditText Family;
    ImageView Image;
    String img=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        Title = findViewById(R.id.et_Title);
        Kingdom = findViewById(R.id.et_Kingdom);
        Type = findViewById(R.id.et_Type);
        Class = findViewById(R.id.et_Class);
        Detachment = findViewById(R.id.et_Detachment);
        Family = findViewById(R.id.et_Family);
        Image = findViewById(R.id.Image);
    }

    public void onClickAdd(View v)
    {
        try
        {
            URL url = new URL("https://ngknn.ru:5101/NGKNN/БорисоваЕА/api/Animals");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(connection)));
           // writer.write();
            //{
            //    "Title": Title, "Kingdom": Kingdom, "Type": Type, "Class": Class, "Detachment": Detachment, "Family": Family, "Image": Image
           // }


        }
        catch (Exception exception)
        {

        }
    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}