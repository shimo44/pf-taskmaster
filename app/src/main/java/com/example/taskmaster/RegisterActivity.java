package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    EditText textUsername;
    EditText textPassword;
    EditText textPasswordConf;
    Button buttonRegister;
    TextView textViewBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textUsername = (EditText)findViewById(R.id.username);
        textPassword = (EditText)findViewById(R.id.password);
        textPasswordConf = (EditText)findViewById(R.id.password_conf);
        buttonRegister = (Button)findViewById(R.id.btn_register);
        textViewBackToLogin = (TextView)findViewById(R.id.already_registered);
        textViewBackToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

    }
}
