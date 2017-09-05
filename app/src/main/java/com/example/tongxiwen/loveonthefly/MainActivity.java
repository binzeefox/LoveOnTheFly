package com.example.tongxiwen.loveonthefly;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.tongxiwen.loveonthefly.heartview.HeartView;

public class MainActivity extends AppCompatActivity {

    private HeartView heartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        heartView = (HeartView) findViewById(R.id.heart_view);
        findViewById(R.id.change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heartView.changeFilled();
                vibrator.cancel();
            }
        });
    }
}
