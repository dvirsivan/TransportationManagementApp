package com.example.TransportationManagement.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.TransportationManagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    private String email;
    private String password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private SharedPreferences sharedPreferences;

    /**
     * onCreate function. if exist sharedPreferences - write it. else - create it
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        ((EditText)findViewById(R.id.email)).setText(sharedPreferences.getString("email",""));
        ((EditText)findViewById(R.id.password)).setText(sharedPreferences.getString("password",""));

    }

    /**
     * listener for onLogin Button. check if the user is already registered
     * @param view - the Button clicked
     */
    public void onLoginClick(View view) {
        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful() ){
                if(sharedPreferences.getString("email","") == "") // if it not written - write it
                    shared();
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);

            }
            else
                Toast.makeText(getBaseContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
        });


    }

    /**
     * register the user and send verification
     * @param view the view clicked
     * @throws InterruptedException
     */
    public void onSignUpClick(View view) throws InterruptedException {
        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        if (validFields() == "") {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            sendMail(); // for verification
                            shared();
                        } else {
                            Toast.makeText(getBaseContext(), task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
            Toast.makeText(getBaseContext(), validFields(), Toast.LENGTH_LONG);
    }

    /**
     * write the email and password in sharedPreferences
     */
    private void shared() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * validation about inputs
     * @return message - the fields that wrong. if it empty - the input proper
     */
    private String validFields() {
        String message = "";
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            message = "Mail is sent to you, please verify the mail";
        if (password.length() < 6 )
            message = "The password have to be a least 6 symbols";
        return message;
    }

    /**
     * send mail for verification
     */
    private void sendMail() {
        currentUser.sendEmailVerification().addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(getBaseContext(),"Mail has sent to you, please verify your mail",Toast.LENGTH_LONG).show();
                if (currentUser.isEmailVerified()) {
                    Toast.makeText(getBaseContext(), "Excellent! You can login now", Toast.LENGTH_LONG).show();
                }

            }
            else {
                Log.d("TAG", task.getException().getMessage());
                Toast.makeText(getBaseContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}