package com.example.taskmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    EditText textUsername;
    EditText textPassword;
    Button buttonLogin;
    TextView textViewRegister;
    DatabaseWorker db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseWorker(this);

        textUsername = (EditText)findViewById(R.id.username);
        textPassword = (EditText)findViewById(R.id.password);
        buttonLogin = (Button)findViewById(R.id.btn_login);
        textViewRegister = (TextView)findViewById(R.id.register);
        textViewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = textUsername
            }
        });


    }
}
