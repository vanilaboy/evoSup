package timbercrew.org.evosup;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Integer BEGIN_TIME = 22;
    private float step;
    private TextView PASSCODE;
    private String chars;
    private Random random;
    private CircularProgressBar progressBar;
    private Chronometer chronometer;
    private TextView time;
    private Integer counterSec = 22;
    private int elapsedSec = 1000;
    private int elapsedFull = BEGIN_TIME * 1000;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PASSCODE = (TextView)findViewById(R.id.passcode);
        chronometer = (Chronometer)findViewById(R.id.chronometer2);
        chars = "abcdefghjklmnop";
        chars = chars.toUpperCase();
        time = (TextView) findViewById(R.id.time);
        time.setText(counterSec.toString());
        random = new Random(System.currentTimeMillis());

        generatePass();

        progressBar = (CircularProgressBar)findViewById(R.id.custom_progressBar);
        step = 255 / BEGIN_TIME;
        progressBar.setProgress(-205);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();

                if (elapsedMillis > elapsedSec) {
                    elapsedSec += 1000;
                    progressBar.setProgressWithAnimation(progressBar.getProgress() + step);
                    counterSec--;
                    time.setText(counterSec.toString());
                }
                if(elapsedMillis > elapsedFull) {
                    elapsedFull += (BEGIN_TIME * 1000);
                    progressBar.setNullWithAnimation();
                    generatePass();
                }
            }
        });
    }

    private void generatePass() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            res.append(chars.charAt(random.nextInt(chars.length())));
        }
        PASSCODE.setText(res.toString());
        counterSec = 22;
        time.setText(counterSec.toString());

    }
}
