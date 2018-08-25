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

public class RegisterFragment extends Fragment {

    private final String TAG = RegisterFragment.class.getSimpleName();

    private MaterialButton registerButton;
    private MaterialButton alreadyRegisteredButton;
    private TextInputEditText registerEmailEditText;
    private TextInputEditText registerPasswordEditText;

    private FirebaseAuth firebaseAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);


        registerButton = v.findViewById(R.id.register_button);
        registerEmailEditText = v.findViewById(R.id.register_email_edit_text);
        registerPasswordEditText = v.findViewById(R.id.register_password_edit_text);
        alreadyRegisteredButton = v.findViewById(R.id.already_registered_button);

        // The registerButton was pressed
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Grab the email and password
                String email = registerEmailEditText.getText().toString().trim();
                final String password = registerPasswordEditText.getText().toString().trim();

                // Make sure email is entered
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make sure password is entered
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Password must be 8 characters
                if (password.length() < 8) {
                    Toast.makeText(getContext(), "Password Must Contain 8 Characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        getActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(),
                                            "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                // Navigate to the MainActivity
                                Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
                                startActivity(mainActivityIntent);
                            }
                        }
                );
            }
        });

        // The alreadyRegistered button was pressed
        alreadyRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        return v;
    }
}
