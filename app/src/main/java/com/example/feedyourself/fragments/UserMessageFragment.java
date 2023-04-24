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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Message;
import com.example.feedyourself.adapters.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMessageFragment extends Fragment {

    private EditText emailEditText;
    private EditText messageEditText;
    private Button sendMessageButton;
    private FirebaseAuth mAuth;
    private DatabaseReference messagesRef;

    private RecyclerView messageHistoryRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
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

        messageList = new ArrayList<>();

        messageHistoryRecyclerView = view.findViewById(R.id.message_history_recycler_view);
        messageHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(messageList);
        messageHistoryRecyclerView.setAdapter(messageAdapter);

        loadMessages();

        return view;
    }

    private void loadMessages() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "Please sign in to view message history.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userEmail = currentUser.getEmail();
        messagesRef.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null && message.getRecipientEmail().equals(userEmail)) {
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load message history.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendMessage() {
        String recipientEmail = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        if (recipientEmail.isEmpty() || message.isEmpty()) {
            Toast.makeText(getContext(), "Both email and message fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(recipientEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            Toast.makeText(getContext(), "The provided email address is not registered.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Continue with sending the message
                            sendValidatedMessage(recipientEmail, message);
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to validate the email address.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendValidatedMessage(String recipientEmail, String message) {
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
