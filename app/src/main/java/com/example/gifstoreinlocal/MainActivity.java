package com.example.gifstoreinlocal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://media0.giphy.com/media/QUQsXD1BakfcZbGFTQ/") // REMEMBER TO END with /
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        textView=(TextView)findViewById(R.id.tv_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });

    }

    //How To Call
    public void download() {
        ServerAPI api = retrofit.create(ServerAPI.class);
        api.downlload("giphy.gif?cid=ecf05e47e971c864ded2f2e77f9c2a930c81e1e87b4abb47&rid=giphy.gif").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    File root = android.os.Environment.getExternalStorageDirectory();
                    File dir = new File (root.getAbsolutePath());
                    dir.mkdirs();
                    File file = new File(dir,
                            "gifnamee"+ ".gif");
//                    File file = new File(path, "file_name.jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    writeFilte(response.body().bytes(), fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(MainActivity.this, "Downloaded successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.getMessage();
                    Toast.makeText(MainActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Downloaded Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeFilte(byte[] data, FileOutputStream bis) {
        try {
            bis.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
