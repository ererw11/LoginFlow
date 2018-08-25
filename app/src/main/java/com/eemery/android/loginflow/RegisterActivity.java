package com.eemery.android.loginflow;

import androidx.fragment.app.Fragment;

public class RegisterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return RegisterFragment.newInstance();
    }
}
