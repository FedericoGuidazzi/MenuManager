package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.android.Database.userRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private userRepository userRepository;
    private int addedUser = 0;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView confirmPassword =(TextView) findViewById(R.id.confermapassword);

        MaterialButton registerButton =(MaterialButton) findViewById(R.id.registerButton);
        MaterialButton loginButton = (MaterialButton) findViewById(R.id.loginButton);

        userRepository = new userRepository(this.getApplication());


        //check the datas and insert user if everything is okay
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the input are filled
                if(username.getText().toString().equals("") || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("") ){
                    Toast.makeText(RegisterActivity.this, "You must compile all the inputs", Toast.LENGTH_SHORT).show();
                //check if password and confirmPassword have the same string
                }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "You must insert the same password", Toast.LENGTH_SHORT).show();
                } else {
                    checkUsername(username.getText().toString(), password.getText().toString());
                }
            }
        });


        //show login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });

    }

    public void showHomepage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goBackToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //function that checks if the username is already taken, if not, add the user and show the home
    public void checkUsername(String username, String password){
        LiveData<List<User>> userList = userRepository.getUsers();
        userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                int usernameAvailable = 1;
                for(User user : users){
                    if(user.username.equals(username)){
                        usernameAvailable = 0;
                        if(addedUser == 1){
                            userId = user.id;
                            setUserid();
                        }
                        break;
                    }
                }
                //username is free, add the user
                if (usernameAvailable == 1){
                    insertNewUser(username, password);
                    addedUser = 1;
                    showHomepage();
                    return;
                }else if(addedUser == 0){
                    //username is not free, toast shown
                    Toast.makeText(RegisterActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    //insert new user in the db
    public void insertNewUser(String username, String password){
        User user = new User();
        user.username=username;
        user.password=password;
        user.profileImage = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +"ic_baseline_account_circle_24.xml").toString();
        userRepository.addUser(user);
    }

    public void setUserid(){
        ((GlobalClass) this.getApplication()).setUserId(userId);
    }
}
