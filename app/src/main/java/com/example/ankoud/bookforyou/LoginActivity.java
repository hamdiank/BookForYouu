package com.example.ankoud.bookforyou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editEmail;
    private EditText editPassword;
    private TextView textViewSignup;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressdialog = new ProgressDialog(this);
        firebaseAuth =FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
        //tester si il est connecter  , go to profile page
            finish();
            startActivity(new Intent(this,ProfileActivity.class));

        }

        buttonLogin  = (Button) findViewById(R.id.buttonLogin) ;
        editEmail = (EditText) findViewById(R.id.editEmail) ;
        editPassword = (EditText)findViewById(R.id.editpassword) ;
        textViewSignup = (TextView) findViewById(R.id.textviewSignup) ;

        buttonLogin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

    }
    private void login(){
        String email = editEmail.getText().toString().trim() ;
        String password = editPassword.getText().toString().trim() ;
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressdialog.setMessage("Login To Your Account...");
        progressdialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        }else {
                            Toast.makeText(LoginActivity.this,"You Don't Have account",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

 }

    @Override
    public void onClick(View v) {
       if(v==buttonLogin){
           login() ;
       }
        if(v==textViewSignup){
            finish();
            startActivity(new Intent(this , MainActivity.class));
        }

    }
}
