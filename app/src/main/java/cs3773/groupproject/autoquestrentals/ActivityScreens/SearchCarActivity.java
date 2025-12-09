package cs3773.groupproject.autoquestrentals.ActivityScreens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cs3773.groupproject.autoquestrentals.R;

public class SearchCarActivity extends AppCompatActivity {

    private EditText searchCar, pickupDate, returnDate;
    private Spinner carTypeSpinner, transmissionTypeSpinner, priceRangeSpinner;
    private ImageButton carImage1, carImage2, carImage3, carImage4, carImage5, carImage6, carImage7, carImage8;
    private ImageView likeCarImage1, likeCarImage2, likeCarImage3, likeCarImage4, likeCarImage5, likeCarImage6, likeCarImage7, likeCarImage8;

    private final Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);

        // Initialize Views
        searchCar = findViewById(R.id.searchCar);
        pickupDate = findViewById(R.id.pickupDate);
        returnDate = findViewById(R.id.returnDate);
        //carTypeSpinner = findViewById(R.id.carTypeSpinner);
        //transmissionTypeSpinner = findViewById(R.id.transmissionTypeSpinner);
        //priceRangeSpinner = findViewById(R.id.priceRangeSpinner);

        // Car Image Buttons
        carImage1 = findViewById(R.id.carImage1);
        carImage2 = findViewById(R.id.carImage2);
        carImage3 = findViewById(R.id.carImage3);
        carImage4 = findViewById(R.id.carImage4);
        carImage5 = findViewById(R.id.carImage5);
        carImage6 = findViewById(R.id.carImage6);
        carImage7 = findViewById(R.id.carImage7);
        carImage8 = findViewById(R.id.carImage8);

        // Like Car Image Views
        likeCarImage1 = findViewById(R.id.likeCarImage1);
        likeCarImage2 = findViewById(R.id.likeCarImage2);
        likeCarImage3 = findViewById(R.id.likeCarImage3);
        likeCarImage4 = findViewById(R.id.likeCarImage4);
        likeCarImage5 = findViewById(R.id.likeCarImage5);
        likeCarImage6 = findViewById(R.id.likeCarImage6);
        likeCarImage7 = findViewById(R.id.likeCarImage7);
        likeCarImage8 = findViewById(R.id.likeCarImage8);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Setup Date Pickers for Pickup and Return Dates
        pickupDate.setOnClickListener(v -> showDatePickerDialog(pickupDate));
        returnDate.setOnClickListener(v -> showDatePickerDialog(returnDate));

        // Set up Image Button click listeners for car details and "like" functionality
        setupCarImageButtonListeners();
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    editText.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void setupCarImageButtonListeners() {
        carImage1.setOnClickListener(v -> goToCarDetailPage(1));
        carImage2.setOnClickListener(v -> goToCarDetailPage(2));
        carImage3.setOnClickListener(v -> goToCarDetailPage(3));
        carImage4.setOnClickListener(v -> goToCarDetailPage(4));
        carImage5.setOnClickListener(v -> goToCarDetailPage(5));
        carImage6.setOnClickListener(v -> goToCarDetailPage(6));
        carImage7.setOnClickListener(v -> goToCarDetailPage(7));
        carImage8.setOnClickListener(v -> goToCarDetailPage(8));

        likeCarImage1.setOnClickListener(v -> toggleLikeCar(1));
        likeCarImage2.setOnClickListener(v -> toggleLikeCar(2));
        likeCarImage3.setOnClickListener(v -> toggleLikeCar(3));
        likeCarImage4.setOnClickListener(v -> toggleLikeCar(4));
        likeCarImage5.setOnClickListener(v -> toggleLikeCar(5));
        likeCarImage6.setOnClickListener(v -> toggleLikeCar(6));
        likeCarImage7.setOnClickListener(v -> toggleLikeCar(7));
        likeCarImage8.setOnClickListener(v -> toggleLikeCar(8));
    }

    private void goToCarDetailPage(int carId) {
        // Intent to navigate to the CarDetailActivity
        Intent intent = new Intent(this, CarDetailsActivity.class);
        intent.putExtra("carId", carId);  // Pass the carId as an extra
        startActivity(intent);  // Start the CarDetailActivity
    }

    private void toggleLikeCar(int carId) {
        ImageView likeIcon = getLikeIconByCarId(carId);
        if (likeIcon != null) {
            // Toggle the heart icon between empty and filled
            if (likeIcon.getTag() != null && likeIcon.getTag().equals("liked")) {
                likeIcon.setImageResource(R.drawable.ic_heart);
                likeIcon.setTag(null);
            } else {
                likeIcon.setImageResource(R.drawable.ic_heartsize);
                likeIcon.setTag("liked");
            }
        }
    }

    private ImageView getLikeIconByCarId(int carId) {
        switch (carId) {
            case 1:
                return likeCarImage1;
            case 2:
                return likeCarImage2;
            case 3:
                return likeCarImage3;
            case 4:
                return likeCarImage4;
            case 5:
                return likeCarImage5;
            case 6:
                return likeCarImage6;
            case 7:
                return likeCarImage7;
            case 8:
                return likeCarImage8;
            default:
                return null;
        }
    }
}