package com.amnesty.panicbutton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import com.amnesty.panicbutton.model.SMSSettings;
import com.amnesty.panicbutton.sms.SMSSettingsActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.settings_layout)
public class SettingsActivity extends RoboActivity {

    public static final int HAPTIC_FEEDBACK_DURATION = 3000;
    @InjectView(R.id.activate_alert)
    private Button activateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActivateButton();
    }

    private void initActivateButton() {
        SMSSettings smsSettings = SMSSettings.retrieve(this);
        activateButton.setEnabled(smsSettings.isConfigured());
    }

    public void launchSmsActivity(View view) {
        startActivity(new Intent(this, SMSSettingsActivity.class));
    }

    public void activateAlert(View view) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(HAPTIC_FEEDBACK_DURATION);
        getMessageAlerter().start();
    }

    MessageAlerter getMessageAlerter() {
        return new MessageAlerter(this);
    }
}