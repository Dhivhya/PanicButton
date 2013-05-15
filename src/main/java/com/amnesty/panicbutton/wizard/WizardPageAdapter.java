package com.amnesty.panicbutton.wizard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.sms.SMSSettingsFragment;

import java.util.ArrayList;
import java.util.List;

import static com.amnesty.panicbutton.R.layout.*;
import static com.amnesty.panicbutton.R.string.*;
import static com.amnesty.panicbutton.wizard.SimpleFragment.create;

public class WizardPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public WizardPageAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(create(wizard_start_screen, start_action));
        fragments.add(create(wizard_password_screen, save_action));
        fragments.add(SMSSettingsFragment.create(R.string.sms_settings_wizard_header));
        fragments.add(create(wizard_emergency_alert1, next_action));
        fragments.add(create(wizard_emergency_alert2, next_action));
        fragments.add(create(wizard_emergency_alert3, next_action));
        fragments.add(new WizardFinishFragment());
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }
}
