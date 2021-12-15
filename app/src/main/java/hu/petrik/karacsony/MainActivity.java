package hu.petrik.karacsony;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView szamlalo;
    private Timer myTimer;
    private Date karacsonyDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    private void init() {
        szamlalo = findViewById(R.id.szamlalo);
        Date now = Calendar.getInstance().getTime();
        int year = now.getYear();
        if (now.getMonth() == 11 && now.getDate() >= 23) {
            year++;
        }
        karacsonyDatum = new Date(year, 11, 25);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date now = Calendar.getInstance().getTime();
                long hatralevoIdo = karacsonyDatum.getTime() - now.getTime();

                long masodpercMili = 1000;
                long percMili = masodpercMili * 60;
                long oraMili = percMili * 60;
                long napMili = oraMili * 24;

                long nap = hatralevoIdo / napMili;
                hatralevoIdo = hatralevoIdo % napMili;
                long ora = hatralevoIdo / oraMili;
                hatralevoIdo = hatralevoIdo % oraMili;
                long perc = hatralevoIdo / percMili;
                hatralevoIdo = hatralevoIdo % percMili;
                long masodperc = hatralevoIdo / masodpercMili;

                String hatralevoSzoveg = getString(R.string.szamlaloFormatum, nap, ora, perc, masodperc);
                runOnUiThread(() -> szamlalo.setText(hatralevoSzoveg));
            }
        };
        myTimer.schedule(task, 0, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myTimer.cancel();
    }
}