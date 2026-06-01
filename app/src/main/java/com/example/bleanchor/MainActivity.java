package com.example.bleanchor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;
    private Button toggleButton;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.status_text);
        toggleButton = findViewById(R.id.toggle_button);

        // 启动即开始广播
        startService();
        updateUI();

        toggleButton.setOnClickListener(v -> {
            if (isRunning) {
                stopService();
            } else {
                startService();
            }
            updateUI();
        });
    }

    private void startService() {
        Intent intent = new Intent(this, AnchorService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        isRunning = true;
    }

    private void stopService() {
        Intent intent = new Intent(this, AnchorService.class);
        stopService(intent);
        isRunning = false;
    }

    private void updateUI() {
        if (isRunning) {
            statusText.setText(R.string.status_running);
            toggleButton.setText(R.string.stop_btn);
        } else {
            statusText.setText(R.string.status_stopped);
            toggleButton.setText(R.string.start_btn);
        }
    }
}
