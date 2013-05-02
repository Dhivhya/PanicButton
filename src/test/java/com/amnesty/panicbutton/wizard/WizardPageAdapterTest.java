package com.amnesty.panicbutton.wizard;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.amnesty.panicbutton.sms.SMSSettingsFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import roboguice.activity.RoboFragmentActivity;

import static com.amnesty.panicbutton.R.layout.*;
import static com.amnesty.panicbutton.R.string.next_action;
import static com.amnesty.panicbutton.R.string.start_action;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class WizardPageAdapterTest {
    private WizardPageAdapter wizardPageAdapter;

    @Before
    public void setUp() {
        FragmentManager fragmentManager = new RoboFragmentActivity().getSupportFragmentManager();
        wizardPageAdapter = new WizardPageAdapter(fragmentManager);
    }

    @Test
    public void shouldReturnTheFragmentsInWizard() {
        assertEquals(6, wizardPageAdapter.getCount());

        assertSimpleFragment(0, wizard_start_screen, start_action);
        assertEquals(SMSSettingsFragment.class, wizardPageAdapter.getItem(1).getClass());
        assertSimpleFragment(2, wizard_emergency_alert1, next_action);
        assertSimpleFragment(3, wizard_emergency_alert2, next_action);
        assertSimpleFragment(4, wizard_emergency_alert3, next_action);
        assertEquals(FinishWizardFragment.class, wizardPageAdapter.getItem(5).getClass());
    }

    private void assertSimpleFragment(int screenIndex, int layoutId, int actionId) {
        SimpleFragment simpleFragment = (SimpleFragment) wizardPageAdapter.getItem(screenIndex);
        Bundle arguments = simpleFragment.getArguments();
        assertEquals(layoutId, arguments.getInt("layout_id"));
        assertEquals(actionId, arguments.getInt("action_id"));
    }
}
