package com.eemery.android.loginflow;


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
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PasswordResetFragment extends Fragment {

    private MaterialButton resetEmailButton;
    private MaterialButton backButton;
    private TextInputEditText resetEmailEditText;

    private FirebaseAuth firebaseAuth;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    public static PasswordResetFragment newInstance() {
        PasswordResetFragment passwordResetFragment = new PasswordResetFragment();
        return passwordResetFragment;
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
        View v = inflater.inflate(R.layout.fragment_password_reset, container, false);

        resetEmailButton = v.findViewById(R.id.submit_reset_email_button);
        backButton = v.findViewById(R.id.back_button);
        resetEmailEditText = v.findViewById(R.id.reset_email_edit_text);

        // The resetEmailButton was pressed
        resetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Grab the email address
                final String email = resetEmailEditText.getText().toString().trim();

                // Make sure the email is not empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Please Enter Registered Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    // Password reset failed
                                    Toast.makeText(getContext(), "Failed To Send Password Reset Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Password reset successful
                                    Toast.makeText(getContext(), "Password Reset Email Was Sent To " + email, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        // The backButton was pressed
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

}
