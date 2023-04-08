package com.example.feedyourself.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import android.Manifest;
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
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserInfoFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 101;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button Logout;
    private ImageView profileImage;
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
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        return binding.getRoot();
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
        FragmentLogin login = new FragmentLogin();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, login).commit();
    }

    private void fetchUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            binding.profileEmail.setText("Email: " + email);
            binding.profileName.setText("Name: " + displayName);

            // Load the user's profile image
            String userId = currentUser.getUid();
            fireStore.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String imageUrl = documentSnapshot.getString("profileImageUrl");
                            if (imageUrl != null) {
                                // Load the image with a placeholder and/or an image loading animation
                                RequestOptions requestOptions = new RequestOptions()
                                        .placeholder(R.drawable.default_image) // Replace with your default profile image drawable resource
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop();

                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .apply(requestOptions)
                                        .into(profileImage);
                            }
                        }
                    });
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.choose_from_gallery:
                    openGallery();
                    return true;
                case R.id.take_selfie:
                    takeSelfie();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
    }

    private void requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void takeSelfie() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestWriteExternalStoragePermission();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivityForResult(intent, TAKE_PHOTO_REQUEST);
            } else {
                Toast.makeText(requireContext(), "No camera app found!", Toast.LENGTH_SHORT).show();
            }
        }
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
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeSelfie();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required to take a selfie", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeSelfie();
            } else {
                Toast.makeText(requireContext(), "Write external storage permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
