package com.amnesty.panicbutton.twitter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.common.MessageFragment;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.amnesty.panicbutton.twitter.TwitterShortCodeFragment.ShortCodeSelectedListener;

public class TwitterSettingsActivity extends RoboFragmentActivity implements ShortCodeSelectedListener {
    private boolean isShortCodeConfigured;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_settings_layout);
        loadSettings();
    }

    private void loadSettings() {
        optTwitterCheckbox.setChecked(TwitterSettings.isEnabled(this));
    }

    public void toggleTwitterSettings(View view) {
        boolean checked = optTwitterCheckbox.isChecked();
        messageLayout.setVisibility(checked && isShortCodeConfigured ? VISIBLE : INVISIBLE);
        shortCodeLayout.setVisibility(checked ? VISIBLE : INVISIBLE);
    }

    public void onSave(View view) {
        if(!optTwitterCheckbox.isChecked()) {
            TwitterSettings.disable(this);
            return;
        }
        TwitterSettings.enable(this);
        ShortCodeSettings shortCodeSettings = twitterShortCodeFragment.getShortCodeSettings();
        TwitterSettings twitterSettings = new TwitterSettings(shortCodeSettings, twitterMessageFragment.getMessage());
        TwitterSettings.save(this, twitterSettings);
    }

    @Override
    public void onShortCodeSelection(boolean successFlag) {
        isShortCodeConfigured = successFlag;
        messageLayout.setVisibility(successFlag ? VISIBLE : INVISIBLE);
    }

    @InjectView(R.id.opt_twitter_checkbox)
    private CheckBox optTwitterCheckbox;

    @InjectView(R.id.twitter_short_code_layout)
    private ViewGroup shortCodeLayout;

    @InjectView(R.id.twitter_message_layout)
    private ViewGroup messageLayout;

    @InjectFragment(R.id.twitter_short_code_fragment)
    private TwitterShortCodeFragment twitterShortCodeFragment;

    @InjectFragment(R.id.twitter_message_fragment)
    private MessageFragment twitterMessageFragment;
}