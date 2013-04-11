package com.amnesty.panicbutton.model;

import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class SMSSettingsTest {

    @Test
    public void shouldSaveAndRetrieveSMSSettings() throws Exception {
        Context context = Robolectric.application;

        List<String> expectedPhoneNumbers = new ArrayList<String>();
        expectedPhoneNumbers.add("123456789");
        expectedPhoneNumbers.add("987654321");
        String expectedMessage = "Test Message";

        SMSSettings smsSettings = new SMSSettings(expectedPhoneNumbers, expectedMessage);

        SMSSettings.save(context, smsSettings);
        SMSSettings retrievedSMSSettings = SMSSettings.retrieve(context);

        assertEquals(expectedMessage, retrievedSMSSettings.message());
        assertEquals(expectedPhoneNumbers.get(0), retrievedSMSSettings.phoneNumberAt(0));
        assertEquals(expectedPhoneNumbers.get(1), retrievedSMSSettings.phoneNumberAt(1));
    }

    @Test
    public void shouldReturnEmptyPhoneNumberForNonExistentIndex() {
        SMSSettings smsSettings = new SMSSettings(new ArrayList<String>(), "msg");
        assertEquals("", smsSettings.phoneNumberAt(1));
    }

    @Test
    public void shouldMaskPhoneNumberExceptTheLastTwoChars() {
        List<String> phoneNumbers = Arrays.asList("111-222-2289", "123456789", "*******89", "1", "22", "", null);
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, "some message");

        assertEquals("**********89", smsSettings.maskedPhoneNumberAt(0));
        assertEquals("*******89", smsSettings.maskedPhoneNumberAt(1));
        assertEquals("*******89", smsSettings.maskedPhoneNumberAt(2));
        assertEquals("1", smsSettings.maskedPhoneNumberAt(3));
        assertEquals("22", smsSettings.maskedPhoneNumberAt(4));
        assertEquals("", smsSettings.maskedPhoneNumberAt(5));
        assertNull(smsSettings.maskedPhoneNumberAt(6));
    }
}
