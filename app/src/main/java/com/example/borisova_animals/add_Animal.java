package com.example.borisova_animals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                Image.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)Image.getDrawable()).getBitmap();
                encodeImg(bitmap);
            }
        }
    }
    public void getImage(View v)
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }
    public String encodeImg(Bitmap bitmap) {
        int prevW = 500;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return img = "";
    }

    public void AddAnimal(View v)
    {
        //try{

            String title = Title.getText().toString();
            String kingdom = Kingdom.getText().toString();
            String type = Type.getText().toString();
            String clas = Class.getText().toString();
            String detachment = Detachment.getText().toString();
            String family = Family.getText().toString();
                post(title, kingdom, type, clas, detachment, family, img, v);


        /*}
        catch (Exception ex)
        {
            Toast.makeText(add_Animal.this, "Косяк!", Toast.LENGTH_SHORT).show();
        }*/
    }
    private void post(String title, String kingdom, String type, String clas, String detachment, String family, String img, View v)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Animal modal = new Animal(null,title, kingdom, type, clas, detachment, family, img);
        Call<Animal> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                    Toast.makeText(add_Animal.this, "Успешное добавление!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(add_Animal.this, "Косяк!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}