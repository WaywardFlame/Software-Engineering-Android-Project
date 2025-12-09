package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cs3773.groupproject.autoquestrentals.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView accountDetails;
    private TextView pastRentals;
    private TextView languageText;
    private RelativeLayout notificationToggle;
    private boolean notificationsEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        profileName = findViewById(R.id.profile_name);
        accountDetails = findViewById(R.id.account_details); // make sure this ID exists in XML
        pastRentals = findViewById(R.id.past_rentals);       // make sure this ID exists in XML
        languageText = findViewById(R.id.language_text);     // make sure this ID exists in XML
        notificationToggle = findViewById(R.id.notification_toggle);

        // Edit button click event to update profile information
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() { // set an ID in XML
            @Override
            public void onClick(View v) {
                updateProfileInfo();
            }
        });

        // Language dropdown click event
        findViewById(R.id.language_dropdown).setOnClickListener(new View.OnClickListener() { // set an ID in XML
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        // Notification toggle click event
        notificationToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotifications();
            }
        });
    }

    private void updateProfileInfo() {
        // Simulating user profile update
        profileName.setText("Updated User Name");
        accountDetails.setText("Email: user@example.com\nPhone: +1 123 456 7890"); // make sure accountDetails ID matches XML
        pastRentals.setText("Updated Car\nDate: 11.02.2024 - 11.05.2024\nPayment: 550.50"); // make sure pastRentals ID matches XML
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
    }

    private void showLanguageDialog() {
        final String[] languages = {"English", "Spanish", "French", "German"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");
        builder.setItems(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                languageText.setText(languages[which]); // make sure languageText ID matches XML
            }
        });
        builder.show();
    }

    private void toggleNotifications() {
        notificationsEnabled = !notificationsEnabled;
        String message = notificationsEnabled ? "Notifications are turned on" : "Notifications are turned off";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
