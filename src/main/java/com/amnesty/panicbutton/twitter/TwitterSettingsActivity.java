package com.amnesty.panicbutton.twitter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.wizard.ActionButtonStateListener;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;

public class TwitterSettingsActivity extends RoboFragmentActivity implements ActionButtonStateListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_settings_layout);
        boolean isEnabled = TwitterSettings.isEnabled(this);
        optTwitterCheckbox.setChecked(isEnabled);
        twitterSettingsFragment.setVisibility(isEnabled);
    }

    public void toggleTwitterSettings(View view) {
        twitterSettingsFragment.setVisibility(optTwitterCheckbox.isChecked());
    }


    public void onSave(View view) {
        if (!optTwitterCheckbox.isChecked()) {
            TwitterSettings.disable(this);
            return;
        }
        TwitterSettings.enable(this);
        twitterSettingsFragment.save();
    }

    @Override
    public void onActionStateChanged(boolean state) {
        saveButton.setEnabled(state);
    }

    @InjectView(R.id.twitter_save_button)
    private Button saveButton;

    @InjectView(R.id.opt_twitter_checkbox)
    private CheckBox optTwitterCheckbox;

    @InjectFragment(R.id.twitter_settings_fragment)
    private TwitterSettingsFragment twitterSettingsFragment;
}