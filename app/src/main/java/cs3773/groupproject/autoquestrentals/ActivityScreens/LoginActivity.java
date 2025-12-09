package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cs3773.groupproject.autoquestrentals.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    // Hardcoded username and password for testing
    private static final String HARD_CODED_EMAIL = "test@example.com";
    private static final String HARD_CODED_PASSWORD = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerTextView = findViewById(R.id.register);

        loginButton.setOnClickListener(v -> attemptLogin());

        registerTextView.setOnClickListener(v -> {
            Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signupIntent);
        });
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the entered email and password match the hardcoded values
        if (HARD_CODED_EMAIL.equals(email) && HARD_CODED_PASSWORD.equals(password)) {
            // Successfully logged in, start HomeActivity
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        } else {
            // Invalid credentials
            Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
        }
    }
}