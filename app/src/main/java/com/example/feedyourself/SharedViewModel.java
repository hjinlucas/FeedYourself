package com.example.feedyourself;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<HashMap<String,String>> data = new MutableLiveData<>(new HashMap<String, String>());
    public void add(String email, String password){
        HashMap<String, String> map = new HashMap<>();
        map.put("Email", email);
        map.put("Name", password);
        data.setValue(map);
    }
    public LiveData<HashMap<String, String>> get(){
        return data;
    }
    public void clear(){
        data.setValue(new HashMap<>());
    }



}
