package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import cs3773.groupproject.autoquestrentals.R;
import cs3773.groupproject.autoquestrentals.ActivityScreens.LoginActivity;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button loginButton = findViewById(R.id.btn_log_in);
        Button joinNowButton = findViewById(R.id.btn_join_now);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainScreenActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainScreenActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}