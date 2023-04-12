package com.example.feedyourself.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.feedyourself.R;
import com.example.feedyourself.databinding.FragmentUserInfoBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserInfoFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    static final int TAKE_PHOTO_REQUEST = 2;
    private static final int PICK_BACKGROUND_IMAGE_REQUEST = 3;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button Logout;
    private ImageView threeDotMenu;
    private ImageView profileImage;
    private ImageButton profileButton;
    private StorageReference storageReference;
    private FirebaseFirestore fireStore;
    private FragmentUserInfoBinding binding;
    GoogleSignInOptions gso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("596346761447-togmbri8f6169ivhlmumoop3iqo3jg6v.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Initialize the firestore variable before calling fetchUserInfo()
        fireStore = FirebaseFirestore.getInstance();
        fetchUserInfo();

        profileImage = binding.profileImage;
        threeDotMenu = binding.threeDotMenu;
        profileButton = binding.imageButton;
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, R.menu.profile_popup_menu);
            }
        });

        threeDotMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, R.menu.three_dot_popup_menu);
            }
        });

        return binding.getRoot();
    }

    private void showPopupMenu(View view, int menuResource) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(menuResource, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.choose_from_gallery:
                    openGallery();
                    return true;
                case R.id.take_photo:
                    requestCameraPermission();
                    return true;
                case R.id.edit_user_name:
                    showEditUsernameDialog();
                    return true;
                case R.id.change_background_image:
                    openBackgroundGallery();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        binding.logoutButton.setOnClickListener(V -> {
            logOut();
        });
    }

    private void logOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        LoginFragment login = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, login).commit();
    }

    private void fetchUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            binding.profileEmail.setText("Email: " + email);

            // Load the user's profile data
            String userId = currentUser.getUid();
            fireStore.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Load the user's name
                            String userName = documentSnapshot.getString("userName");
                            if (userName != null) {
                                binding.userName.setText(userName);
                            } else {
                                binding.userName.setText(displayName);
                            }

                            // Load the user's profile image
                            String imageUrl = documentSnapshot.getString("profileImageUrl");
                            if (imageUrl != null) {
                                RequestOptions requestOptions = new RequestOptions()
                                        .placeholder(R.drawable.default_image)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop();

                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .apply(requestOptions)
                                        .into(profileImage);
                            }

                            // Load the user's background image
                            String backgroundImageUrl = documentSnapshot.getString("backgroundImageUrl");
                            if (backgroundImageUrl != null) {
                                RequestOptions requestOptions = new RequestOptions()
                                        .placeholder(R.drawable.top_background_gradient)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop();

                                Glide.with(requireContext())
                                        .load(backgroundImageUrl)
                                        .apply(requestOptions)
                                        .into(binding.topBackground);
                            }
                        }
                    });
        }
    }



    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO_REQUEST);
        } else {
            openCamera();
        }
    }



    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = null;
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
            } else if (requestCode == TAKE_PHOTO_REQUEST && data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    imageUri = getImageUri(requireContext(), imageBitmap);
                }
            }
            if (imageUri != null) {
                updateProfileImage(imageUri);
            }
            if (requestCode == PICK_BACKGROUND_IMAGE_REQUEST && data != null && data.getData() != null) {
                Uri backgroundImageUri = data.getData();
                updateTopBackgroundImage(backgroundImageUri);
            }
        }
    }

    private void updateTopBackgroundImage(Uri backgroundImageUri) {
        // Update the ImageView with the new background image
        Glide.with(requireContext())
                .load(backgroundImageUri)
                .centerCrop()
                .into(binding.topBackground);

        // Save the new background image to Firebase Storage
        String userId = mAuth.getCurrentUser().getUid();
        StorageReference backgroundRef = storageReference.child("background_images/" + userId + ".jpg");
        backgroundRef.putFile(backgroundImageUri).addOnSuccessListener(taskSnapshot -> {
            // Get the download URL and update the user's background image URL in Firebase Firestore
            backgroundRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String backgroundImageUrl = uri.toString();
                updateUserBackgroundImageUrl(userId, backgroundImageUrl);
            });
        });
    }

    private void updateUserBackgroundImageUrl(String userId, String backgroundImageUrl) {
        DocumentReference userDocRef = fireStore.collection("users").document(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("backgroundImageUrl", backgroundImageUrl);

        userDocRef.set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Background image URL updated successfully
                })
                .addOnFailureListener(e -> {
                    // Error updating background image URL
                });
    }

    private Uri getImageUri(Context context, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "Title", null);
        return Uri.parse(path);
    }

    private void updateProfileImage(Uri imageUri) {
        // Update the ImageView with the new image
        Glide.with(requireContext())
                .load(imageUri)
                .centerCrop()
                .into(profileImage);

        // Save the new image to Firebase Storage
        String userId = mAuth.getCurrentUser().getUid();
        StorageReference imageRef = storageReference.child(userId + ".jpg");
        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            // Get the download URL and update the user's profile image URL in Firebase Firestore
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                updateUserProfileImageUrl(userId, imageUrl);
            });
        });
    }

    private void updateUserProfileImageUrl(String userId, String imageUrl) {
        DocumentReference userDocRef = fireStore.collection("users").document(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("profileImageUrl", imageUrl);

        userDocRef.set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Profile image URL updated successfully
                })
                .addOnFailureListener(e -> {
                    // Error updating profile image URL
                });
    }

    private void showEditUsernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Username");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newUsername = input.getText().toString();
            updateUserName(newUsername);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateUserName(String newUserName) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Update the user's name in Firestore
            fireStore.collection("users").document(userId).update("userName", newUserName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(requireContext(), "User name updated successfully", Toast.LENGTH_SHORT).show();
                        // Update the displayed user name
                        binding.userName.setText(newUserName);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Failed to update user name", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void openBackgroundGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Background Image"), PICK_BACKGROUND_IMAGE_REQUEST);
    }
}
