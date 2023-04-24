package com.example.feedyourself.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.feedyourself.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class UserMessageFragment extends Fragment {

    private EditText emailEditText;
    private EditText messageEditText;
    private Button sendMessageButton;
    private FirebaseAuth mAuth;
    private DatabaseReference messagesRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_message_fragment, container, false);

        emailEditText = view.findViewById(R.id.email_edit_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendMessageButton = view.findViewById(R.id.send_message_button);

        mAuth = FirebaseAuth.getInstance();
        messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        sendMessageButton.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void sendMessage() {
        String recipientEmail = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        if (recipientEmail.isEmpty() || message.isEmpty()) {
            Toast.makeText(getContext(), "Both email and message fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "Please sign in to send a message.", Toast.LENGTH_SHORT).show();
            return;
        }

        String senderEmail = currentUser.getEmail();
        HashMap<String, Object> messageData = new HashMap<>();
        messageData.put("senderEmail", senderEmail);
        messageData.put("recipientEmail", recipientEmail);
        messageData.put("message", message);
        messageData.put("timestamp", System.currentTimeMillis());

        messagesRef.push().setValue(messageData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Message sent successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to send message.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
