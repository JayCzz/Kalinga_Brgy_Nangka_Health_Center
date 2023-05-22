package com.example.brgynangkahealthcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NurseLogin extends AppCompatActivity {


    private EditText loginUsername, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;
    private CheckBox rememberMeCheckBox; // Declare the checkbox
    boolean passwordVisible;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nurse_login);

        loginUsername = findViewById(R.id.username);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox); // Initialize the checkbox

        loginPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, @NonNull MotionEvent event){
                final int Right=2;
                if (event.getRawX()>=loginPassword.getRight()-loginPassword.getCompoundDrawables()[Right].getBounds().width()){
                    int selection=loginPassword.getSelectionEnd();
                    if (passwordVisible){
                        // set hide password drawable
                        loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        // set show password drawable image
                        loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                        passwordVisible=false;
                    } else {
                        // set show password drawable image
                        loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                        // set show password drawable
                        loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible=true;
                    }
                    loginPassword.setSelection(selection);
                    return true;
                }
                return false;
            }
        });

        // Implement remember me checkbox logic
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isRemembered = preferences.getBoolean("isRemembered", false);
        if (isRemembered) {
            String savedUsername = preferences.getString("savedUsername", "");
            String savedPassword = preferences.getString("savedPassword", "");
            loginUsername.setText(savedUsername);
            loginPassword.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                    // Save login info if "Remember Me" checkbox is checked
                    if (rememberMeCheckBox.isChecked()) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isRemembered", true);
                        editor.putString("savedUsername", loginUsername.getText().toString());
                        editor.putString("savedPassword", loginPassword.getText().toString());
                        editor.apply();
                    }
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NurseLogin.this, NurseRegistration.class);
                startActivity(intent);
            }
        });
    }
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Nurse");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);


                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);

                        String firstnameFromDB = snapshot.child(userUsername).child("firstname").getValue(String.class);
                        String lastnameFromDB = snapshot.child(userUsername).child("lastname").getValue(String.class);
                        String birthdayFromDB = snapshot.child(userUsername).child("birthday").getValue(String.class);
                        String ageFromDB = snapshot.child(userUsername).child("age").getValue(String.class);
                        String sexFromDB = snapshot.child(userUsername).child("sex").getValue(String.class);
                        String IDNumberFromDB = snapshot.child(userUsername).child("IDNumber").getValue(String.class);
                        String phoneFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);

                        Intent intent = new Intent(NurseLogin.this, NurseProfilePi.class);

                        intent.putExtra("firstname", firstnameFromDB);
                        intent.putExtra("lastname", lastnameFromDB);
                        intent.putExtra("IDNumber", IDNumberFromDB);

                        intent.putExtra("birthday", birthdayFromDB);
                        intent.putExtra("age", ageFromDB);
                        intent.putExtra("sex", sexFromDB);
                        startActivity(intent);

                        intent = new Intent(NurseLogin.this, NurseProfileAi.class);

                        intent.putExtra("firstname", firstnameFromDB);
                        intent.putExtra("lastname", lastnameFromDB);
                        intent.putExtra("IDNumber", IDNumberFromDB);

                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("password", passwordFromDB);


                    } else {
                        loginPassword.setError("Invalid Password Incorrect");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}