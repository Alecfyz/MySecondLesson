package ru.gb.hiandroid.mysecondlesson;

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

/*    private Button switchToExtendButton;
    private TextView resultTV;
    private int counter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalcText = findViewById(R.id.view_result);
        setNumberButtonListeners();
        if (savedInstanceState == null) { //First launch

            calculator = new Calculator();

            logCycle("onCreate First Launch");
        } else {
            logCycle("onCreate Recreate Launch");
            calculator = savedInstanceState.getParcelable(CALC_STRING);
            CalcText.setText(calculator.getCurString());
        }

/*        switchToExtendButton = findViewById(R.id.switch_to_extended_button);

        switchToExtendButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ExtendedActivity.class);
            startActivity(intent);
            resultTV.setText(String.valueOf(++counter));
        });*/

        findViewById(R.id.add_operation_button).setOnClickListener(v -> {
            logCycle("Key < + > pressed");
            calculator.readkey("+");
            CalcText.setText(calculator.getCurString());
        });
        findViewById(R.id.minus_operation_button).setOnClickListener(v -> {
            logCycle("Key < - > pressed");
            calculator.readkey("-");
            CalcText.setText(calculator.getCurString());
        });
        findViewById(R.id.div_operation_button).setOnClickListener(v -> {
            logCycle("Key < / > pressed");
            calculator.readkey("/");
            CalcText.setText(calculator.getCurString());
        });
        findViewById(R.id.mult_operation_button).setOnClickListener(v -> {
            logCycle("Key < * > pressed");
            calculator.readkey("*");
            CalcText.setText(calculator.getCurString());
        });
        findViewById(R.id.get_result_button).setOnClickListener(v -> {
            logCycle("Key < = > pressed");
            calculator.readkey("=");
            CalcText.setText(String.valueOf(calculator.getResult()));
        });
        findViewById(R.id.dot_symbol_button).setOnClickListener(v -> {
            logCycle("Key < . > pressed");
            calculator.readkey(".");
            CalcText.setText(String.valueOf(calculator.getCurString()));
        });

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

    private final int[] numberButtonIds = new int[]{R.id.digit_zero_button, R.id.digit_one_button, R.id.digit_two_button, R.id.digit_three_button,
            R.id.digit_four_button, R.id.digit_five_button, R.id.digit_six_button, R.id.digit_seven_button, R.id.digit_eight_button, R.id.digit_nine_button};


    private void setNumberButtonListeners() {
        for (int i = 0; i < numberButtonIds.length; i++) {
            findViewById(numberButtonIds[i]).setOnClickListener(v -> {
                Button btn = (Button)v;
                String btnStrng = btn.getTag().toString();
                logCycle("Key <" + btnStrng + "> pressed");
                calculator.readkey(btnStrng);
                CalcText.setText(calculator.getCurString());
            });
        }
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