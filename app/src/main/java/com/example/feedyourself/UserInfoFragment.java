package com.example.feedyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.feedyourself.databinding.FragmentUserInfoBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button Logout;
    private FragmentUserInfoBinding binding;
    GoogleSignInOptions gso;
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
