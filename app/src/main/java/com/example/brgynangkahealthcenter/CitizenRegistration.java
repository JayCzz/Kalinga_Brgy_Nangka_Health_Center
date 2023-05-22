package com.example.brgynangkahealthcenter;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitizenRegistration extends AppCompatActivity {

    EditText signupFirstname, signupLastname, signupBirthday, signupAge, signupFamilynumber, signupEmail, signupPhone, signupUsername, signupPassword, signupConfirmpassword;
    TextView loginRedirect;
    ProgressBar progressBar;
    RadioGroup radioGroupRegisterGender;
    RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog picker;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_citizen_registration);


        progressBar = findViewById(R.id.progressBar);
        signupFirstname = findViewById(R.id.firstname);
        signupLastname = findViewById(R.id.lastname);
        signupBirthday= findViewById(R.id.birthday);
        signupAge= findViewById(R.id.age);
        signupFamilynumber = findViewById(R.id.familynumber);
        signupEmail = findViewById(R.id.email);
        signupPhone = findViewById(R.id.phone);
        signupUsername = findViewById(R.id.username);
        signupPassword = findViewById(R.id.password);
        signupConfirmpassword = findViewById(R.id.confirmpassword);
        loginRedirect = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.SignupButton);

        radioGroupRegisterGender = findViewById(R.id.gender_register);
        radioGroupRegisterGender.clearCheck();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        signupBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(CitizenRegistration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        signupBirthday.setText(dayofMonth + "/" + (month+ 1)+"/"+ year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String firstname = signupFirstname.getText().toString();
                String lastname = signupLastname.getText().toString();
                String birthday = signupBirthday.getText().toString();
                String age = signupAge.getText().toString();
                String familynumber = signupFamilynumber.getText().toString();
                String email = signupEmail.getText().toString();
                String phone = signupPhone.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String confirm_password = signupConfirmpassword.getText().toString();

                //Validate register mobile number using matcher and pattern
                String mobileRegex = "^(09|\\+639)\\d{9}$";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(phone);


                // Validate if any of the fields are empty
                if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(birthday)
                        || TextUtils.isEmpty(age) || TextUtils.isEmpty(familynumber) || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(confirm_password)) {
                    Toast.makeText(CitizenRegistration.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mobileMatcher.find()){
                    Toast.makeText(CitizenRegistration.this, "Mobile No. is not valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                if (selectedGenderId == -1) {
                    Toast.makeText(CitizenRegistration.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                String gender = radioButtonRegisterGenderSelected.getText().toString();

                // Validate if the password and confirm password fields match
                if (!password.equals(confirm_password)) {
                    Toast.makeText(CitizenRegistration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate if the age is a positive integer
                try {
                    int intAge = Integer.parseInt(age);
                    if (intAge < 0) {
                        Toast.makeText(CitizenRegistration.this, "Age should be a positive integer", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(CitizenRegistration.this, "Age should be a positive integer", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(firstname, lastname, birthday, gender, familynumber, age, email, phone, username, password, confirm_password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(CitizenRegistration.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CitizenRegistration.this, CitizenLogin.class);
                startActivity(intent);

            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CitizenRegistration.this, CitizenLogin.class);
                startActivity(intent);
            }
        });

    }
}