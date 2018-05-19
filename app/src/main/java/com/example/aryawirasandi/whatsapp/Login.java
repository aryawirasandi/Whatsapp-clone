package com.example.aryawirasandi.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aryawirasandi.whatsapp.models.User;
import com.example.aryawirasandi.whatsapp.services.ApiService;
import com.example.aryawirasandi.whatsapp.tools.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean checklogin = Preferences.getBooleanPreference("login", getApplicationContext());

        if(checklogin){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
    }

    public void login(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(username.equals("")||password.equals(""))
        {
            Toast.makeText(this, "Salah satu tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else
        {
            Call<User> loginCall = ApiService.service.login(username, password);

            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful())
                    {
                        User userResponse = response.body();
                        if(userResponse.getStatus().equals("success"))
                        {
                            Preferences.setBooleanPreference("login", true, getApplicationContext());
                            Preferences.setStringPreference("username", userResponse.getData().getUsername()
                            ,getApplicationContext());
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Login.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }
}
