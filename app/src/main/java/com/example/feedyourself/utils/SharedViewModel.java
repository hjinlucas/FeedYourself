package com.example.feedyourself.utils;

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

public class SharedViewModel<K, V> extends ViewModel {
    private MutableLiveData<HashMap<K, V>> data = new MutableLiveData<>(new HashMap<K, V>());

    public void add(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        data.setValue(map);
    }

    public LiveData<HashMap<K, V>> get() {
        return data;
    }

    public void clear() {
        data.setValue(new HashMap<>());
    }
}
