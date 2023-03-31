package com.example.feedyourself.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.feedyourself.R;
import com.example.feedyourself.databinding.FragmentUserInfoBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 1;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button Logout;
    private FragmentUserInfoBinding binding;
    GoogleSignInOptions gso;
    private ImageView userImageView;

    private SharedViewModel sharedViewModel;
    public UserInfoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("596346761447-togmbri8f6169ivhlmumoop3iqo3jg6v.apps.googleusercontent.com")
                .requestEmail()
                .build();
        fetchUserInfo();
        userImageView = binding.getRoot().findViewById(R.id.profile_image);
        userImageView.setOnClickListener(this::showPopupMenu);

        return binding.getRoot();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile_image:
                    openGallery();
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
    private void logOut(){
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        FragmentLogin login = new FragmentLogin();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, login).commit();


    }
    private void fetchUserInfo(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            binding.profileEmail.setText("Email: " + email);
            binding.profileName.setText("Name: " + displayName);
        }
    }
}
