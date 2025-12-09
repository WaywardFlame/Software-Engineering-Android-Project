package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import cs3773.groupproject.autoquestrentals.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton homeButton = findViewById(R.id.home_button);
        ImageButton profileButton = findViewById(R.id.profile_button);
        ImageButton lookupButton = findViewById(R.id.lookup_button);
        ImageButton bookingButton = findViewById(R.id.booking_button);
        ImageButton helpButton = findViewById(R.id.help_button);

        homeButton.setOnClickListener(view ->
                Toast.makeText(HomeActivity.this, "You are already on the Home screen", Toast.LENGTH_SHORT).show());

        profileButton.setOnClickListener(view ->
                navigateTo(ProfileActivity.class, "Profile"));

        lookupButton.setOnClickListener(view ->
                navigateTo(SearchCarActivity.class, "Search car"));

        bookingButton.setOnClickListener(view ->
                navigateTo(TransactionActivity.class, "Transaction"));

        helpButton.setOnClickListener(view ->
                navigateTo(HelpActivity.class, "Help"));

        setupCarItemActions();
    }

    private void navigateTo(Class<?> activityClass, String activityName) {
        Intent intent = new Intent(HomeActivity.this, activityClass);
        startActivity(intent);
        Toast.makeText(this, "Navigating to " + activityName, Toast.LENGTH_SHORT).show();
    }

    private void setupCarItemActions() {
        View teslaCard = findViewById(R.id.tesla_card);
        if (teslaCard != null) {
            teslaCard.setOnClickListener(view ->
                    Toast.makeText(HomeActivity.this, "Tesla Model S clicked", Toast.LENGTH_SHORT).show());
        }

        View ferrariCard = findViewById(R.id.ferrari_card);
        if (ferrariCard != null) {
            ferrariCard.setOnClickListener(view ->
                    Toast.makeText(HomeActivity.this, "Ferrari sg90 clicked", Toast.LENGTH_SHORT).show());
        }
    }
}