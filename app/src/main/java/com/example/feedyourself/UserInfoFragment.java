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
import com.google.firebase.auth.FirebaseAuth;

public class UserInfoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button Logout;
    private FragmentUserInfoBinding binding;

    private SharedViewModel sharedViewModel;
    public UserInfoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding.logoutButton.setOnClickListener(V -> {


            FragmentLogin login = new FragmentLogin();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, login).commit();
            mAuth.signOut();
        });
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.get().observe(getViewLifecycleOwner(),data -> {
            String email = data.get("Email");
            String name = data.get("Name");
            binding.profileEmail.setText("Email: " + email);
            binding.profileName.setText("Name: " + name);
        });


    }
}
