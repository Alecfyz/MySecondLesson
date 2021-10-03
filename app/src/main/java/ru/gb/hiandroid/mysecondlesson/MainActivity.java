package ru.gb.hiandroid.mysecondlesson;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import model.Calculator;

public class MainActivity extends AppCompatActivity {
    private static final String CALC_STRING = "CALC_STRING";
    private TextView resultTextTV;
    private Calculator calculator;
    private static String screeenOrientation;
    private static boolean isThemeNight;

    private static final String TAG = "@@@ MainActivity";

    private ActivityResultLauncher<Intent> settingsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setLocalTheme();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        screeenOrientation = getScreenOrientation();

        initView();
        initCalculator(savedInstanceState);


        setNumberButtonListeners();
        setOpButtonsListeners();
        setAdditionButtonsListeners();
        prepareLaunchers();


        //Log.d(TAG, "OnCreate MainActivity");
    }

    private void setLocalTheme() {
        if (isThemeNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
            logCycle(" AfterRestart = " + isThemeNight);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();

            logCycle(" AfterRestart = " + isThemeNight);

        }


    }

    private void prepareLaunchers() {
        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Boolean isNewThemeNight = Boolean.valueOf(data.getStringExtra(SettingsActivity.SETTINGS_ISNIGHT_EVAL_THEME_EXTRA_KEY));
                isThemeNight = isNewThemeNight;
                Toast.makeText(this, "Returned theme = " + isNewThemeNight, Toast.LENGTH_SHORT).show();
                setLocalTheme();
            }
        });
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
        if (screeenOrientation.equals("land")) { // we have no "Settings" button in land layout. Sad but true.
            return;
        }
        findViewById(R.id.switch_to_settings_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(SettingsActivity.SETTINGS_ISNIGHT_THEME_EXTRA_KEY, isThemeNight);
            settingsLauncher.launch(intent);
        });
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
        String logScreeenOrientation = screeenOrientation.equals("land") ? "Horizontal orientation" : "Vertical orientation";

        Log.d(TAG + " (" + logScreeenOrientation + ")", message);
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