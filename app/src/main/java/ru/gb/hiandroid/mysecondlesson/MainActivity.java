package ru.gb.hiandroid.mysecondlesson;

import android.content.Intent;
import android.content.res.Configuration;
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
    private TextView resultTextTV;
    private Calculator calculator;
    private static String screeenOrientation;

    private static final String TAG = "@@@ MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screeenOrientation = getScreenOrientation().equals("land") ? "Horizontal orientation" : "Vertical orientation";

        initView();
        initCalculator(savedInstanceState);

        setNumberButtonListeners();
        setOpButtonsListeners();
        setAdditionButtonsListeners();

        Log.d(TAG, "OnCreate MainActivity");
    }

    void initCalculator(Bundle savedInstanceState) {
        if (savedInstanceState == null) { //First launch

            calculator = new Calculator();

            logCycle("onCreate First Launch");
        } else {
            logCycle("onCreate Recreate Launch");
            calculator = savedInstanceState.getParcelable(CALC_STRING);
            resultTextTV.setText(calculator.getCurString());
        }
    }

    private void initView() {
        resultTextTV = findViewById(R.id.view_result);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logCycle("OnDestroy MainActivity");
    }

    private final int[] numberButtonIds = new int[]{R.id.digit_zero_button, R.id.digit_one_button, R.id.digit_two_button, R.id.digit_three_button,
            R.id.digit_four_button, R.id.digit_five_button, R.id.digit_six_button, R.id.digit_seven_button, R.id.digit_eight_button, R.id.digit_nine_button};


    private void setNumberButtonListeners() {
        for (int numberButtonId : numberButtonIds) {
            findViewById(numberButtonId).setOnClickListener(v -> {
                Button btn = (Button) v;
                String btnStrng = btn.getTag().toString();
                logCycle("Key <" + btnStrng + "> pressed");
                calculator.readkey(btnStrng);
                resultTextTV.setText(calculator.getCurString());
            });
        }
    }

    private void setAdditionButtonsListeners() {
        findViewById(R.id.switch_to_settings_button).setOnClickListener(v -> {
            boolean isThemeNight = isNightThemeActive(); // todo
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(SettingsActivity.SETTINGS_ISNIGHT_THEME_EXTRA_KEY, isThemeNight);

        });
    }

    private boolean isNightThemeActive() {
        return false;
    }

    private void setOpButtonsListeners() {
        findViewById(R.id.add_operation_button).setOnClickListener(v -> {
            logCycle("Key < + > pressed");
            calculator.readkey("+");
            resultTextTV.setText(calculator.getCurString());
        });
        findViewById(R.id.minus_operation_button).setOnClickListener(v -> {
            logCycle("Key < - > pressed");
            calculator.readkey("-");
            resultTextTV.setText(calculator.getCurString());
        });
        findViewById(R.id.div_operation_button).setOnClickListener(v -> {
            logCycle("Key < / > pressed");
            calculator.readkey("/");
            resultTextTV.setText(calculator.getCurString());
        });
        findViewById(R.id.mult_operation_button).setOnClickListener(v -> {
            logCycle("Key < * > pressed");
            calculator.readkey("*");
            resultTextTV.setText(calculator.getCurString());
        });
        findViewById(R.id.get_result_button).setOnClickListener(v -> {
            logCycle("Key < = > pressed");
            calculator.readkey("=");
            resultTextTV.setText(String.valueOf(calculator.getResult()));
        });
        findViewById(R.id.dot_symbol_button).setOnClickListener(v -> {
            logCycle("Key < . > pressed");
            calculator.readkey(".");
            resultTextTV.setText(String.valueOf(calculator.getCurString()));
        });
        findViewById(R.id.clear_button).setOnClickListener(v -> {
            logCycle("Key < ALL CLEAR > pressed");
            calculator.readkey("AC");

            resultTextTV.setText(String.valueOf(calculator.getCurString()));
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CALC_STRING, calculator);
        super.onSaveInstanceState(outState);
    }

    private void logCycle(String message) {
        Log.d(TAG + " (" + screeenOrientation + ")", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return "port";
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return "land";
        else
            return "";
    }

}