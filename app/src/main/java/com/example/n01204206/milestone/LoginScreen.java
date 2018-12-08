package com.example.n01204206.milestone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private EditText loginUsername, loginPassword;
    private Button login_button;
    private TextView forgotPassword, signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.createAccount);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(LoginScreen.this, Main2Activity.class);
                startActivity(loginPage);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(LoginScreen.this, ForgotPassword.class);
                startActivity(loginPage);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(LoginScreen.this, RegisterActivity.class);
                startActivity(loginPage);
            }
        });
        handleLogin();
        findAllViewsfromLayout();
    }
    private void handleLogin(){
        mAuth = FirebaseAuth.getInstance();

     login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginUser(String.valueOf(loginUsername.getText()),String.valueOf(loginPassword.getText()));
            }
        });

    }

    private void findAllViewsfromLayout(){
        login_button = findViewById(R.id.login_button);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Toast.makeText(getApplicationContext(),"Already Logged In",Toast.LENGTH_LONG).show();
        }

    }



    private void loginUser(String username, String password){

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Log.d("MapleLeaf","signIn Success");
                    FirebaseUser user = mAuth.getCurrentUser();
                   Toast.makeText(getApplicationContext(),"You are now logged in",Toast.LENGTH_LONG).show();

                   Intent intent = new Intent(LoginScreen.this,Main2Activity.class);
                   startActivity(intent);
                } else{
                    Log.w("MapleLeaf","signInWithUsername:failure",task.getException());
                    Toast.makeText(getApplicationContext(),"Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}