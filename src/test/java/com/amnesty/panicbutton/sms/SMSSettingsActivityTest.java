package com.amnesty.panicbutton.sms;

import android.widget.Button;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.fragment.SMSSettingsFragment;
import com.amnesty.panicbutton.model.SMSSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class SMSSettingsActivityTest {
    private SMSSettingsActivity smsSettingsActivity;
    private Button saveButton;
    @Mock
    private SMSSettingsFragment mockSMSSettingsFragment;

    @Before
    public void setup() {
        initMocks(this);
        smsSettingsActivity = new SMSSettingsActivity() {
            SMSSettingsFragment getSMSSettingsFragment() {
                return mockSMSSettingsFragment;
            }
        };
        smsSettingsActivity.onCreate(null);
        saveButton = (Button) smsSettingsActivity.findViewById(R.id.save_button);
    }

    @Test
    public void shouldSaveSettingsOnSave() {
        saveButton.performClick();
        verify(mockSMSSettingsFragment).performAction();
    }

    @Test
    public void shouldReturnSMSSettingsFragmentObject() {
        SMSSettingsActivity activity = new SMSSettingsActivity();
        activity.onCreate(null);
        assertNotNull(activity.getSMSSettingsFragment());
    }
}
