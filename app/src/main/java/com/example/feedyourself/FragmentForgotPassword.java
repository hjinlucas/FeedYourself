package com.example.feedyourself;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentForgotPassword extends Fragment {
    TextInputEditText editTextEmail;
    Button recoverBtn;
    Button btn_goToLogin;
    FirebaseAuth mAuth;
    RelativeLayout progressBar;


    public FragmentForgotPassword() {
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get references to UI elements
        editTextEmail = view.findViewById(R.id.emailBox);
        recoverBtn = view.findViewById(R.id.recoverBtn);

        // Set click listener for reset button
        recoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                email = String.valueOf(editTextEmail.getText());

                // Validate input field
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required.");
                    return;
                }

                // Send password reset email to user's email address
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(getContext(), "Password reset email sent. Check your email inbox!", Toast.LENGTH_SHORT).show();
                                    // Navigate to another fragment or activity, e.g. login fragment/activity
                                    FragmentLogin fragmentLogin = new FragmentLogin();
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, fragmentLogin)
                                            .commit();
                                } else {
                                    // If sending email fails, display a message to the user.
                                    String errorMessage = task.getException().getMessage();
                                    Log.e("RegisterFragment", "Authentication failed: " + errorMessage);
                                    Toast.makeText(getContext(), "Failed to send password reset email. Please try again.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        return view;
    }
}