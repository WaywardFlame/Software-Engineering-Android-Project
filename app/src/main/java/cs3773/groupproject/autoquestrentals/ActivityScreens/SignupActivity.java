package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.CustomerStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.DatabaseAccess;
import cs3773.groupproject.autoquestrentals.R;

import java.util.Random;

public class SignupActivity extends AppCompatActivity {

    private EditText firstName, middleName, lastName, email, phone, password, confirmPassword;
    private Button registerButton;
    private TextView loginLink;
    private DatabaseAccess dbAccess;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone); // Make sure this EditText is in your layout
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.register);
        loginLink = findViewById(R.id.login);

        // Initialize DatabaseAccess
        dbAccess = new DatabaseAccess(this);
        dbAccess.open(); // Ensure database is opened before performing operations

        registerButton.setOnClickListener(v -> registerUser());

        loginLink.setOnClickListener(v -> {
            Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }

    private void registerUser() {
        String firstNameValue = firstName.getText().toString().trim();
        String middleNameValue = middleName.getText().toString().trim();
        String lastNameValue = lastName.getText().toString().trim();
        String emailValue = email.getText().toString().trim();
        String phoneValue = phone.getText().toString().trim();
        String passwordValue = password.getText().toString().trim();
        String confirmPasswordValue = confirmPassword.getText().toString().trim();

        // Basic validation
        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty() || phoneValue.isEmpty() || passwordValue.isEmpty() || confirmPasswordValue.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordValue.equals(confirmPasswordValue)) {
            Toast.makeText(SignupActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a unique ID for the user
        int userId = new Random().nextInt(1000000); // Ensure this logic is safe for your use case

        // Check if the account already exists
        if (dbAccess.CheckAccountData(userId, emailValue, firstNameValue + lastNameValue)) {
            Toast.makeText(SignupActivity.this, "An account with this email or username already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add account data
        dbAccess.AddAccountData(userId, emailValue, phoneValue, firstNameValue + lastNameValue, passwordValue);
        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

        // Redirect to home activity
        Intent homeIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAccess.close(); // Close database when activity is destroyed
    }
}