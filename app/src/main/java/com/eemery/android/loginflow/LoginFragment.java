package com.eemery.android.loginflow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private MaterialButton resetPasswordButton;
    private MaterialButton registerButton;
    private MaterialButton loginButton;

    private TextInputEditText loginEmailEditText;
    private TextInputEditText loginPasswordEditText;

    private FirebaseAuth firebaseAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        resetPasswordButton = v.findViewById(R.id.login_reset_password_button);
        registerButton = v.findViewById(R.id.login_register_button);
        loginButton = v.findViewById(R.id.login_button);

        loginEmailEditText = v.findViewById(R.id.login_email_edit_text);
        loginPasswordEditText = v.findViewById(R.id.login_password_edit_text);


        // OnClick for the Register Button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // OnClickListener for the Reset Password Button
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetPasswordIntent = new Intent(getContext(), PasswordResetActivity.class);
                startActivity(resetPasswordIntent);
            }
        });

        // OnClickListener for the Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Grab the email and password that was entered
                String email = loginEmailEditText.getText().toString().trim();
                final String password = loginPasswordEditText.getText().toString().trim();

                // Make sure the email address is not empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure the password is not empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticate the user
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // Login Failed
                                    if (password.length() < 8) {
                                        // Password is too short
                                        loginPasswordEditText.setError(
                                                "Password too short, enter minimum 8 characters!");
                                    } else {
                                        Toast.makeText(getContext(),
                                                "Authentication failed, check your email and password or sign up",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Login successful, //TODO: Navigate to MainActivity
//                                    Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
//                                    startActivity(mainActivityIntent);
                                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                );
            }
        });

        return v;
    }
}
