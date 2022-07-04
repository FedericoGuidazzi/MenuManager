package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Database.userRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private userRepository userRepository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton loginButton =(MaterialButton) findViewById(R.id.loginButton);
        MaterialButton registerButton = (MaterialButton) findViewById(R.id.RegisterButton);

        userRepository = new userRepository(this.getApplication());

        //click su login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check sulle credenziali
                isUser(username.getText().toString(), password.getText().toString());

            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        });


    }
    public void openRegistration(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomepage(){
        ((GlobalClass) this.getApplication()).setUserId(userId);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void isUser(String username, String password){
        LiveData<List<User>> userList = userRepository.getUser();
        final boolean[] check = {false};
        userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for(User user : users){
                    if(user.username.equals(username) && user.password.equals(password)) {
                        userId = user.id;
                        openHomepage();
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}