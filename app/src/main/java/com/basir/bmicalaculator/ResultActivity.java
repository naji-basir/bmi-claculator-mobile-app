package com.basir.bmicalaculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import android.graphics.Color;
import android.content.res.ColorStateList;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResultBMI;
    private TextView tvResultCategory;
    private TextView tvResultHeight;
    private TextView tvSuggestedWeight;

    private Button btnBackToCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        tvResultBMI = findViewById(R.id.tvResultBMI);
        tvResultCategory = findViewById(R.id.tvResultCategory);
        tvResultHeight = findViewById(R.id.tvResultHeight);
        tvSuggestedWeight = findViewById(R.id.tvSuggestedWeight);

        btnBackToCalculator = findViewById(R.id.btnBackToCalculator);

        // Receive data from MainActivity
        double bmi = getIntent().getDoubleExtra("BMI", 0);
        double height = getIntent().getDoubleExtra("HEIGHT", 0);

        // Display results
        displayResults(bmi, height);

        // Back to calculator
        btnBackToCalculator.setOnClickListener(v -> finish());

        // Header back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void displayResults(double bmi, double height) {

        // Display BMI
        tvResultBMI.setText(
                String.format(Locale.US, "%.2f", bmi)
        );

        // Display height
        tvResultHeight.setText(
                String.format(Locale.US, "%.1f", height)
        );

        // Determine category and color
        String category;
        int color;

        if (bmi < 18.5) {
            category = "Underweight";
            color = Color.rgb(84, 188, 229);

        } else if (bmi < 25) {
            category = "Normal";
            color = Color.rgb(32, 201, 151);

        } else if (bmi < 30) {
            category = "Overweight";
            color = Color.rgb(244, 193, 93);

        } else {
            category = "Obese";
            color = Color.rgb(255, 133, 27);
        }

        tvResultCategory.setText(category);

        // Change category badge color
        tvResultCategory.setBackgroundTintList(
                ColorStateList.valueOf(color)
        );

        // Calculate suggested adult weight range
        double heightInMeters = height / 100.0;

        double minWeight =
                18.5 * heightInMeters * heightInMeters;

        double maxWeight =
                24.9 * heightInMeters * heightInMeters;

        tvSuggestedWeight.setText(
                String.format(
                        Locale.US,
                        "%.1f ~ %.1f",
                        minWeight,
                        maxWeight
                )
        );
    }
}
