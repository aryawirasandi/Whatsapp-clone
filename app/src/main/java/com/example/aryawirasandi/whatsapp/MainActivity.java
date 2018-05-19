package com.example.aryawirasandi.whatsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryawirasandi.whatsapp.models.Data;
import com.example.aryawirasandi.whatsapp.models.User;
import com.example.aryawirasandi.whatsapp.services.ApiService;
import com.example.aryawirasandi.whatsapp.tools.Preferences;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public TextView Name, Description, PhoneNumber;
    public CircleImageView imageProfil;
    String TAG = "WA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Declaration reference id

        Name = (TextView) findViewById(R.id.namaProfil);
        Description = (TextView) findViewById(R.id.quotes);
        PhoneNumber = (TextView) findViewById(R.id.nomorHp);
        imageProfil = (CircleImageView) findViewById(R.id.gambarProfil);
        String username = Preferences.getStringPreference("username", getApplicationContext());
        String name = Preferences.getStringPreference("nama", getApplicationContext());
        String status = Preferences.getStringPreference("status", getApplicationContext());
        String no_telp = Preferences.getStringPreference("no_telp", getApplicationContext());

        Name.setText(name);
        Description.setText(status);
        PhoneNumber.setText(no_telp);

        final Call<User> userCall = ApiService.service.getUser(username);
//      memanggil server tersebut sehingga terjadi asyctask
        userCall.enqueue(new Callback<User>() {
//          Ketika servernya merespons artinya bisa diakses
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//              Jika berhasil
                if(response.isSuccessful())
                {
                    Log.e("WA", "Berhasil bos ku");
                    User userResponse = response.body();
//                   Jika berhasil mendapatkan status sukses
                    if(userResponse.getStatus().equals("success"))
                    {
                        Data user = userResponse.getData();
                        Name.setText(user.getNama());
                        Description.setText(user.getStatus());
                        PhoneNumber.setText(user.getNoTelp());
//                      get the data
                        Preferences.setStringPreference("nama", user.getNama(), getApplicationContext());
                        Preferences.setStringPreference("status", user.getStatus(), getApplicationContext());
                        Preferences.setStringPreference("no_telp", user.getNoTelp(), getApplicationContext());
                        Picasso.get()
                                .load(user.getImg())
                                .into(imageProfil);

                    }
                }else{
                    Log.e("WA",response.message());
                }
            }
//          Ketika servernya  gagal merespons karena error
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("WA:", t.getMessage());
            }
        });
    }
}
