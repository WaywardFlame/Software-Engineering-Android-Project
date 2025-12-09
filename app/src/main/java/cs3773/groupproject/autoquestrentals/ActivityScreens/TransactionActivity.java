package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cs3773.groupproject.autoquestrentals.R;

public class TransactionActivity extends AppCompatActivity {

    // Declare UI components
    private TextView carName, pickupLocation, dropoffLocation, pickupDate, dropoffDate, transactionDate;
    private TextView customerName, customerEmail, customerPhone;
    private Spinner paymentMethodSpinner;
    private Button downloadReceiptButton, contactSupportButton, cancelButton;
    private ImageView carImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Initialize UI components
        carName = findViewById(R.id.car_name);
        pickupLocation = findViewById(R.id.pickup_location);
        dropoffLocation = findViewById(R.id.dropoff_location);
        pickupDate = findViewById(R.id.pickup_date);
        dropoffDate = findViewById(R.id.dropoff_date);
        transactionDate = findViewById(R.id.transaction_date);
        customerName = findViewById(R.id.customer_name);
        customerEmail = findViewById(R.id.customer_email);
        customerPhone = findViewById(R.id.customer_phone);
        //paymentMethodSpinner = findViewById(R.id.payment_method_spinner);
        downloadReceiptButton = findViewById(R.id.download_receipt_button);
        contactSupportButton = findViewById(R.id.contact_support_button);
        cancelButton = findViewById(R.id.cancel_button);
        carImage = findViewById(R.id.car_image);

        // Set dummy data (replace with real data from a database or API)
        carName.setText("Car: Toyota Camry");
        pickupLocation.setText("Pick-up Location: San Antonio Airport");
        dropoffLocation.setText("Drop-off Location: San Antonio Downtown");
        pickupDate.setText("Pick-up Date: 2024-11-20");
        dropoffDate.setText("Drop-off Date: 2024-11-23");
        transactionDate.setText("Transaction Date: 2024-11-14");
        customerName.setText("Customer Name: John Doe");
        customerEmail.setText("Email: john.doe@example.com");
        customerPhone.setText("Phone: (210) 123-4567");

        // Set the car image (use a drawable resource or load image dynamically)
        carImage.setImageResource(R.drawable.frame231);

        // Button click listeners
        downloadReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransactionActivity.this, "Downloading Receipt...", Toast.LENGTH_SHORT).show();
                // Add logic to download receipt here
            }
        });

        contactSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransactionActivity.this, "Contacting Customer Support...", Toast.LENGTH_SHORT).show();
                // Add logic to contact support here
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransactionActivity.this, "Transaction Canceled", Toast.LENGTH_SHORT).show();
                // Add logic to cancel the transaction here
            }
        });

        // Navigation button click listeners
        ImageButton homeButton = findViewById(R.id.home_button);
        ImageButton profileButton = findViewById(R.id.profile_button);
        ImageButton bookingButton = findViewById(R.id.booking_button);
        ImageButton lookupButton = findViewById(R.id.lookup_button);
        ImageButton helpButton = findViewById(R.id.help_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to home screen
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to profile screen
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to booking screen
            }
        });

        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to lookup screen
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to help screen
            }
        });
    }
}
