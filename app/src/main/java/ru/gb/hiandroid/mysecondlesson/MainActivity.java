package ru.gb.hiandroid.mysecondlesson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "@@@";
    private Button switchToExtendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToExtendButton = findViewById(R.id.switch_to_horiz_button);
        switchToExtendButton.setOnClickListener( v-> {
            Intent intent = new Intent(v.getContext(), ExtendedActivity.class);
            startActivity(intent);
        });


        Log.d(TAG, "OnCreate MainActivity");
        Toast.makeText(getApplicationContext(), "OnCreate MainActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy MainActivity");
    }
}