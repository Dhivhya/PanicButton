package com.amnesty.panicbutton.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.wizard.WizardAction;

import static com.amnesty.panicbutton.R.id.*;

public class SMSSettingsFragment extends NestedFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sms_settings_fragment, container, false);
    }

    @Override
    protected int[] getFragmentIds() {
        return new int[]{first_contact, second_contact, third_contact, sms_message};
    }

    @Override
    public String action() {
        return getString(WizardAction.SAVE.actionId());
    }
}
