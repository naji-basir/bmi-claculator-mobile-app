package com.basir.bmicalaculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etHeight;
    private Spinner spWeightUnit, spHeightUnit;
    private TextView tvBMI, tvCategory;
    private Button btnCalculate;

    // Accepted ranges in standard units
    private static final double MIN_WEIGHT = 3.0;
    private static final double MAX_WEIGHT = 150.0;

    private static final double MIN_HEIGHT = 30.0;
    private static final double MAX_HEIGHT = 220.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);

        spWeightUnit = findViewById(R.id.spWeightUnit);
        spHeightUnit = findViewById(R.id.spHeightUnit);


        btnCalculate = findViewById(R.id.btnCalculate);

        // Handle calculate button click
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI();
            }
        });
    }
    private void calculateBMI() {

        String weightInput = etWeight.getText().toString().trim();
        String heightInput = etHeight.getText().toString().trim();

        if (weightInput.isEmpty() || heightInput.isEmpty()) {
            showError("Please enter both weight and height.");
            return;
        }

        try {

            double weight = Double.parseDouble(weightInput);
            double height = Double.parseDouble(heightInput);

            if (!Double.isFinite(weight) || !Double.isFinite(height)
                    || weight <= 0 || height <= 0) {
                showError("Please enter valid positive numbers.");
                return;
            }

            // Convert weight to kilograms
            String weightUnit = spWeightUnit.getSelectedItem().toString();

            if (weightUnit.equals("lb")) {
                weight *= 0.45359237;
            }

            // Convert height to centimeters
            String heightUnit = spHeightUnit.getSelectedItem().toString();

            if (heightUnit.equals("inch")) {
                height *= 2.54;
            }

            // Validate weight
            if (weight < 3 || weight > 150) {
                showError("Weight must be between 3 and 150 kg.");
                return;
            }

            // Validate height
            if (height < 30 || height > 220) {
                showError("Height must be between 30 and 220 cm.");
                return;
            }

            // Convert centimeters to meters
            double heightInMeters = height / 100.0;

            // Calculate BMI
            double bmi = weight / (heightInMeters * heightInMeters);

            // Open result screen and pass calculated data
            android.content.Intent intent =
                    new android.content.Intent(
                            MainActivity.this,
                            ResultActivity.class
                    );

            intent.putExtra("BMI", bmi);
            intent.putExtra("HEIGHT", height);

            startActivity(intent);

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values.");
        }
    }


    // Display validation errors
    private void showError(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();

        tvBMI.setText("--");
        tvCategory.setText("Invalid input");
    }
}
