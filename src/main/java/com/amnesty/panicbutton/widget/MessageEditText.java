package com.amnesty.panicbutton.widget;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.sms.MessageLimitWatcher;

import static com.amnesty.panicbutton.AppConstants.MAX_CHARACTER_COUNT;

public class MessageEditText extends LinearLayout {
    private EditText messageEditText;
    private TextView messageLimitView;
    private MessageLimitWatcher messageLimitWatcher;

    public MessageEditText(Context context) {
        super(context);
        initializeView();
    }

    public MessageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    private void initializeView() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(service);
        li.inflate(R.layout.message_edit_text, this, true);

        messageLimitView = (TextView) findViewById(R.id.characters_left);
        messageEditText = (EditText) findViewById(R.id.sms_message);

        messageLimitWatcher = new MessageLimitWatcher(messageLimitView, MAX_CHARACTER_COUNT);
        messageEditText.addTextChangedListener(messageLimitWatcher);

        InputFilter[] filters = {new InputFilter.LengthFilter(MAX_CHARACTER_COUNT)};
        messageEditText.setFilters(filters);
        messageLimitView.setText(String.valueOf(MAX_CHARACTER_COUNT - messageEditText.getText().length()));
    }
}
