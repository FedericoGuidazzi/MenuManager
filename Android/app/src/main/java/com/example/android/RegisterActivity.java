package com.example.android;

import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView confermapassword =(TextView) findViewById(R.id.confermapassword);

        MaterialButton registerButton =(MaterialButton) findViewById(R.id.registerButton);
        MaterialButton loginButton = (MaterialButton) findViewById(R.id.loginButton);

        userRepository = new userRepository(this.getApplication());


        //click su register inserisce dati nel db e fa vedere scheramat principale
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //controllo sugli input se sono pieni
                if(username.getText().toString().equals("") || password.getText().toString().equals("") || confermapassword.getText().toString().equals("") ){
                    Toast.makeText(RegisterActivity.this, "You must compile all the inputs", Toast.LENGTH_SHORT).show();
                //controllo se password e conferma password contengono la stessa stringa
                }else if(!password.getText().toString().equals(confermapassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "You must insert the same password", Toast.LENGTH_SHORT).show();
                } else {
                    //controllo se esiste già un utente con quel username
                    if(!checkUsername(username.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Username is already taken", Toast.LENGTH_SHORT).show();
                    } else {
                        insertNewUser(username.getText().toString(), password.getText().toString());
                        showHomepage();

                    }
                }
            }
        });


        //click su login torna indietro
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });

    }

    public void showHomepage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    public void goBackToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //controlla che l'username inserito non sia gia stato utilizzato
    public boolean checkUsername(String username){
        LiveData<List<User>> userList = userRepository.getUser();
        final boolean[] check = {true};
        userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for(User user : users){
                    if(user.username.equals(username)){
                        check[0]= false;
                        break;
                    }
                }
            }
        });
        return check[0];
    }

    //inserisce un nuovo utente nel database
    public void insertNewUser(String username, String password){
        User user = new User();
        user.username=username;
        user.password=password;
        userRepository.addUser(user);
    }
}
