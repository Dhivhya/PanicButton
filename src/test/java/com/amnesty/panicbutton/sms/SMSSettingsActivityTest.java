package com.amnesty.panicbutton.sms;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.model.SMSSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SMSSettingsActivityTest {
    private SMSSettingsActivity smsSettingsActivity;

    private Button saveButton;
    private EditText smsEditText;
    private EditText firstContactEditText;
    private EditText secondContactEditText;
    private EditText thirdContactEditText;

    private String alreadySavedMessage;

    @Before
    public void setup() {
        setupExistingSettings();

        smsSettingsActivity = new SMSSettingsActivity();
        smsSettingsActivity.onCreate(null);

        saveButton = (Button) smsSettingsActivity.findViewById(R.id.save_button);

        Fragment smsFragment = smsSettingsActivity.getSupportFragmentManager().findFragmentById(R.id.sms_message);
        Fragment firstContact = smsSettingsActivity.getSupportFragmentManager().findFragmentById(R.id.first_contact);
        Fragment secondContact = smsSettingsActivity.getSupportFragmentManager().findFragmentById(R.id.second_contact);
        Fragment thirdContact = smsSettingsActivity.getSupportFragmentManager().findFragmentById(R.id.third_contact);

        smsEditText = (EditText) smsFragment.getView().findViewById(R.id.message_edit_text);
        firstContactEditText = (EditText) firstContact.getView().findViewById(R.id.contact_edit_text);
        secondContactEditText = (EditText) secondContact.getView().findViewById(R.id.contact_edit_text);
        thirdContactEditText = (EditText) thirdContact.getView().findViewById(R.id.contact_edit_text);
    }

    private void setupExistingSettings() {
        alreadySavedMessage = "Already saved message";
        List<String> alreadySavedPhoneNumbers = Arrays.asList("123245697", "345665-5656", "45234234345");
        SMSSettings smsSettings = new SMSSettings(alreadySavedPhoneNumbers, alreadySavedMessage);
        SMSSettings.save(Robolectric.application, smsSettings);
    }

    @Test
    public void shouldLoadTheSMSSettingsLayoutOnCreateWithExistingSettings() {
        assertEquals(R.id.sms_settings_layout_root, shadowOf(smsSettingsActivity).getContentView().getId());
        assertEquals(alreadySavedMessage, smsEditText.getText().toString());
        assertEquals("*******97", firstContactEditText.getText().toString());
        assertEquals("*********56", secondContactEditText.getText().toString());
        assertEquals("*********45", thirdContactEditText.getText().toString());
    }

    @Test
    public void shouldSaveSMSSettingsOnSaveClickAndMaskPhoneNumbers() throws Exception {
        String expectedMessage = "Help! I am in trouble";
        String number1 = "123-456-789";
        String number2 = "9874641321";
        String number3 = "4564523423";

        smsEditText.setText(expectedMessage);
        firstContactEditText.setText(number1);
        secondContactEditText.setText(number2);
        thirdContactEditText.setText(number3);

        saveButton.performClick();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("SMS settings saved successfully"));

        SMSSettings retrievedSMSSettings = SMSSettings.retrieve(Robolectric.application);
        assertEquals(expectedMessage, retrievedSMSSettings.getMessage());
        assertEquals(number1, retrievedSMSSettings.getPhoneNumber(0));
        assertEquals(number2, retrievedSMSSettings.getPhoneNumber(1));
        assertEquals(number3, retrievedSMSSettings.getPhoneNumber(2));

        assertEquals("*********89",firstContactEditText.getText().toString());
        assertEquals("********21",secondContactEditText.getText().toString());
        assertEquals("********23",thirdContactEditText.getText().toString());
    }
}
