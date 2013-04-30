package com.amnesty.panicbutton.wizard;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.amnesty.panicbutton.R;
import com.amnesty.panicbutton.fragment.WizardFragment;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@ContentView(R.layout.wizard_layout)
public class WizardActivity extends RoboFragmentActivity {
    private WizardViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;

    @InjectView(R.id.previous_button)
    Button previousButton;
    @InjectView(R.id.action_button)
    Button actionButton;

    private SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            previousButton.setVisibility(position != 0 ? VISIBLE : INVISIBLE);
            setActionButtonText();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previousButton.setVisibility(INVISIBLE);
        actionButton.setText(getString(R.string.start_action));

        viewPager = (WizardViewPager) findViewById(R.id.wizard_view_pager);
        pagerAdapter = getWizardPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    public void performAction(View view) {
        viewPager.performAction();
    }

    private void setActionButtonText() {
        WizardFragment currentFragment = (WizardFragment) pagerAdapter.getItem(viewPager.getCurrentItem());
        actionButton.setText(currentFragment.action());
    }

    public void previous(View view) {
        viewPager.previous();
    }

    FragmentStatePagerAdapter getWizardPagerAdapter() {
        return new WizardPageAdapter(getSupportFragmentManager());
    }
}
