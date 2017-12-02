package timbercrew.org.evosup;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Integer beginTime = 22;
    private int step;
    private TextView PASSCODE;
    private String chars;
    private Random random;
    private ProgressBar progressBar;
    private Chronometer chronometer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PASSCODE = (TextView)findViewById(R.id.passcode);
        chronometer = (Chronometer)findViewById(R.id.chronometer2);
        chars = "abcdefghjklmnop";
        chars = chars.toUpperCase();
        random = new Random(System.currentTimeMillis());

        StringBuilder res = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            res.append(chars.charAt(random.nextInt(chars.length())));
        }
        PASSCODE.setText(res.toString());

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        step = progressBar.getMax() / (beginTime) + 1;
        progressBar.setProgress(0);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();

                if (elapsedMillis > 1000) {
                    progressBar.setProgress(progressBar.getProgress() + step);
                }
            }
        });
    }
}
