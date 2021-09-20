package ru.gb.hiandroid.mysecondlesson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class ExtendedActivity extends AppCompatActivity {
    private static final String TAG = "@@@";
    private Button switchToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended);

        switchToMainButton = findViewById(R.id.switch_to_main_button);
        switchToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        });
        Log.d(TAG, "OnCreate MainActivity");
        Toast.makeText(getApplicationContext(), "OnCreate ExtActivity", Toast.LENGTH_SHORT).show();
    }

}