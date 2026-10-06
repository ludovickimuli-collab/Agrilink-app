package com.agrilink.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView welcome = new TextView(this);

        welcome.setText(
                "🌱 AGRILINK\n\n" +
                "Smart Farming\n" +
                "Sustainable Future\n" +
                "Food Security for All"
        );

        welcome.setTextSize(26);
        welcome.setPadding(40, 100, 40, 40);

        setContentView(welcome);
    }
}
