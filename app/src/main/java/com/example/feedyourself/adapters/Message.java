package com.example.feedyourself.adapters;

public class Message {
    private String senderEmail;
    private String recipientEmail;
    private String message;
    private long timestamp;

    public Message() {
    }

    public Message(String senderEmail, String recipientEmail, String message, long timestamp) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
