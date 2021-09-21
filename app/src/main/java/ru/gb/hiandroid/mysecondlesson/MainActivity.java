package ru.gb.hiandroid.mysecondlesson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import model.Calculator;

public class MainActivity extends AppCompatActivity {
    private static final String CALC_STRING = "CALC_STRING";
    private TextView CalcText;
    private Calculator calculator;

    private static final String TAG = "@@@ MainActivity";
    private static final String COUNTER_KEY = "counter_key";

    private Button switchToExtendButton;
    private TextView resultTV;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(COUNTER_KEY)) {
            counter = savedInstanceState.getInt(COUNTER_KEY);
        } else counter = 0;

//        resultTV = findViewById(R.id.view_result);
//        resultTV.setText(String.valueOf(counter));

/*        switchToExtendButton = findViewById(R.id.switch_to_extended_button);

        switchToExtendButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ExtendedActivity.class);
            startActivity(intent);
            resultTV.setText(String.valueOf(++counter));
        });*/


        Log.d(TAG, "OnCreate MainActivity");
        Toast.makeText(getApplicationContext(), "OnCreate MainActivity ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy MainActivity");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CALC_STRING, calculator);
        super.onSaveInstanceState(outState);
    }

    private void logCycle(String message) {
        Log.d("MainActivity", message);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}