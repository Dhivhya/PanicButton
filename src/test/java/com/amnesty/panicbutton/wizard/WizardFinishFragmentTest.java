package com.amnesty.panicbutton.wizard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import com.amnesty.panicbutton.CalculatorActivity;
import com.amnesty.panicbutton.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowPreferenceManager;
import roboguice.activity.RoboFragmentActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class WizardFinishFragmentTest {
    private Button finishButton;
    private RoboFragmentActivity roboFragmentActivity;

    @Before
    public void setUp() {
        WizardFinishFragment wizardFinishFragment = new WizardFinishFragment();
        roboFragmentActivity = new RoboFragmentActivity();
        FragmentManager fragmentManager = roboFragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(wizardFinishFragment, null);
        fragmentTransaction.commit();
        finishButton = (Button) wizardFinishFragment.getView().findViewById(R.id.finish_wizard_button);
    }

    @Test
    public void shouldSetFirstRunFlagAndNavigateToFacadeOnClickingFinish() {
        finishButton.performClick();
        ShadowActivity shadowActivity = shadowOf(roboFragmentActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertEquals(CalculatorActivity.class.getName(), startedIntent.getComponent().getClassName());
        SharedPreferences sharedPreferences = ShadowPreferenceManager.getDefaultSharedPreferences(Robolectric.application);
        assertFalse(sharedPreferences.getBoolean("FIRST_RUN", true));
    }

    @Test
    public void shouldReturnEmptyAction() {
        WizardFragment fragment = new WizardFragment();
        assertEquals("", fragment.action());
    }
}
