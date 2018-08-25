package com.eemery.android.loginflow;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }
}
