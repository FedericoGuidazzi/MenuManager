package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView confermapassword =(TextView) findViewById(R.id.confermapassword);

        MaterialButton registerButton =(MaterialButton) findViewById(R.id.registerButton);
        MaterialButton loginButton = (MaterialButton) findViewById(R.id.loginButton);

        //click su register inserisce dati nel db e fa vedere scheramat principale

        //click su login torna indietro
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });

    }

    public void goBackToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
