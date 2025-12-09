package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import cs3773.groupproject.autoquestrentals.R;

public class AddReviewActivity extends AppCompatActivity {

    private EditText reviewEditText;
    private RatingBar ratingBar;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review); // Make sure you have this layout

        // Initialize views
        reviewEditText = findViewById(R.id.reviewEditText);
        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.submitButton);

        // Set up submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });
    }

    private void submitReview() {
        // Get the review content and rating
        String reviewContent = reviewEditText.getText().toString();
        float rating = ratingBar.getRating();

        // Simple validation
        if (reviewContent.isEmpty()) {
            Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rating == 0) {
            Toast.makeText(this, "Please provide a rating.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Submit the review (this could involve saving to a database or sending data to an API)
        // For now, we will just display a toast as a placeholder
        Toast.makeText(this, "Review submitted: " + reviewContent + " with rating: " + rating, Toast.LENGTH_LONG).show();

        // Optionally, you can finish the activity and go back to the previous screen
        finish();
    }
}
