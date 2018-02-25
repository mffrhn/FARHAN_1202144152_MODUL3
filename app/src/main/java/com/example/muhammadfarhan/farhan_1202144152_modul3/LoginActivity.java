package com.example.muhammadfarhan.farhan_1202144152_modul3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View view) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
        String a = "EAD";
        String b = "MOBILE";

        if (username.getText().toString().equalsIgnoreCase(a) && password.getText().toString().equalsIgnoreCase(b)){
            myAlert.setTitle("Welcome");
            myAlert.setMessage("Selamat anda berhasil login");
            myAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    String username1 = username.getText().toString();
                    intent.putExtra("username1", username1);

                    String password1 = password.getText().toString();
                    intent.putExtra("password1", password1);
                    startActivity(intent);
                }
            });

            myAlert.show();

        }else {
            myAlert.setTitle("Wrong Password");
            myAlert.setMessage("Username dan Password yang anda masukkan salah, try again...");
            myAlert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "password salah", Toast.LENGTH_LONG).show();
                }
            });
            myAlert.show();
        }
    }
}
