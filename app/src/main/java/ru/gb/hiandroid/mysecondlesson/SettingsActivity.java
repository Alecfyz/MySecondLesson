package ru.gb.hiandroid.mysecondlesson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    static final String SETTINGS_ISNIGHT_THEME_EXTRA_KEY = "IS_NIGHT_SETTINGS";
    static final String SETTINGS_ISNIGHT_EVAL_THEME_EXTRA_KEY = "IS_NIGHT_EVAL_SETTINGS";
    static boolean isNight;
    private Switch nightSwitcher;
    private Button save_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent data = getIntent();

        initSettings();
        fillSettings(data);
        setupSettings();
    }

    private void setupSettings() {

        save_button.setOnClickListener(v -> {
            Intent outData = new Intent();
            boolean isNightEval = nightSwitcher.isChecked();
            outData.putExtra(SETTINGS_ISNIGHT_EVAL_THEME_EXTRA_KEY, String.valueOf(isNightEval));
            setResult(Activity.RESULT_OK, outData);
            finish();
        });
    }

    private void initSettings() {
        nightSwitcher = findViewById(R.id.nightSwither);
        save_button = findViewById(R.id.save_button);
    }

    private void fillSettings(Intent data) {
        isNight = data.getBooleanExtra(SETTINGS_ISNIGHT_THEME_EXTRA_KEY, false);
        nightSwitcher.setChecked(isNight);
        logCycle(String.valueOf(isNight));
    }

    private void logCycle(String message) {
        Log.d("SecondActivity", message);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}