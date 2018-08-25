package com.eemery.android.loginflow;

import androidx.fragment.app.Fragment;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}