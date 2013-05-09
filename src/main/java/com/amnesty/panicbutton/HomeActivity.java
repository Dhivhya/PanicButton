package com.amnesty.panicbutton;

import android.content.Intent;
import android.os.Bundle;
import com.amnesty.panicbutton.wizard.WizardActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

import java.util.Timer;
import java.util.TimerTask;

import static com.amnesty.panicbutton.wizard.ApplicationSettings.isFirstRun;

@ContentView(R.layout.welcome_screen)
public class HomeActivity extends RoboActivity {
    public static final int SPLASH_TIME = 1000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirstRun(this)) {
            scheduleTimer();
            return;
        }
        startFacade();
    }

    private void startFacade() {
        startActivity(new Intent(this, CalculatorActivity.class));
    }

    private void scheduleTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(HomeActivity.this, WizardActivity.class));
            }
        }, SPLASH_TIME);
    }

}