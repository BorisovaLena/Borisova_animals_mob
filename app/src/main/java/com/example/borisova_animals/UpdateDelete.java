package com.example.borisova_animals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

public class UpdateDelete extends AppCompatActivity {

    EditText Title;
    EditText Kingdom;
    EditText Type;
    EditText Class;
    EditText Detachment;
    EditText Family;
    ImageView Image;
    String img=null;
    Bundle arg;
    Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        arg = getIntent().getExtras();
        animal = arg.getParcelable(Animal.class.getSimpleName());
        Title = findViewById(R.id.et_Title);
        Kingdom = findViewById(R.id.et_Kingdom);
        Type = findViewById(R.id.et_Type);
        Class = findViewById(R.id.et_Class);
        Detachment = findViewById(R.id.et_Detachment);
        Family = findViewById(R.id.et_Family);
        Image = findViewById(R.id.Image);
        Title.setText(animal.getTitle());
        Kingdom.setText(animal.getKingdom());
        Type.setText(animal.getType());
        Class.setText(animal.getClas());
        Detachment.setText(animal.getDetachment());
        Family.setText(animal.getFamily());
        //Image.setImageBitmap(getImgBitmap(animal.getImage()));
    }

    /*private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(UpdateDelete.this.getResources(),
                    R.drawable.zaglushka);
        }

    }*/

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

    public void UpdateAnimal(View v)
    {
        try{

        String title = Title.getText().toString();
        String kingdom = Kingdom.getText().toString();
        String type = Type.getText().toString();
        String clas = Class.getText().toString();
        String detachment = Detachment.getText().toString();
        String family = Family.getText().toString();
        put(title, kingdom, type, clas, detachment, family, img);
        SystemClock.sleep(1000);
        onClickBack(v);

        }
        catch (Exception ex)
        {
            Toast.makeText(UpdateDelete.this, "Косяк!", Toast.LENGTH_SHORT).show();
        }
    }

    private void put(String title, String kingdom, String type, String clas, String detachment, String family, String img)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/БорисоваЕА/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPIUpdate update = retrofit.create(RetrofitAPIUpdate.class);
        Animal mm = new Animal(null, title, kingdom, type, clas, detachment, family, img);
        Call<Animal> call = update.updateData(animal.getID(), mm);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                Toast.makeText(UpdateDelete.this, "Успешное изменение", Toast.LENGTH_SHORT).show();
                //Animal responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(UpdateDelete.this, "Косяк!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}