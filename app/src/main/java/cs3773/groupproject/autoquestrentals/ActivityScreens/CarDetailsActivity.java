package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import cs3773.groupproject.autoquestrentals.R;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView carNameTextView;
    private ImageView carImageView;
    private TextView carDescriptionTextView;
    private TextView review1TextView;
    private TextView review2TextView;
    private Button addReviewButton;
    private View swipeToBookView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        // Find views by ID
        carNameTextView = findViewById(R.id.car_name);
        carImageView = findViewById(R.id.car_image);
        carDescriptionTextView = findViewById(R.id.car_description);
        review1TextView = findViewById(R.id.review_1);
        review2TextView = findViewById(R.id.review_2);
        addReviewButton = findViewById(R.id.add_review_button);
        swipeToBookView = findViewById(R.id.swipe_to_book);

        // Get the car details passed via Intent
        Intent intent = getIntent();
        String carName = intent.getStringExtra("car_name");
        String carDescription = intent.getStringExtra("car_description");
        String review1 = intent.getStringExtra("review_1");
        String review2 = intent.getStringExtra("review_2");
        int carImageResId = intent.getIntExtra("car_image", R.drawable.frame231); // Default image if not passed

        // Set the data to views
        carNameTextView.setText(carName);
        carDescriptionTextView.setText(carDescription);
        review1TextView.setText(review1);
        review2TextView.setText(review2);
        carImageView.setImageResource(carImageResId);

        // Handle swipe-to-book functionality
        swipeToBookView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                navigateToTransactionActivity();
            }
        });

        // Handle Add Review button click
        addReviewButton.setOnClickListener(v -> {
            // Handle the add review logic here
            // For example, launch a new activity to add a review
            Intent addReviewIntent = new Intent(this, AddReviewActivity.class);
            addReviewIntent.putExtra("car_name", carName);
            startActivity(addReviewIntent);
        });
    }

    // Method to navigate to TransactionActivity when the swipe occurs
    private void navigateToTransactionActivity() {
        Intent intent = new Intent(this, TransactionActivity.class);
        startActivity(intent);
    }
}
