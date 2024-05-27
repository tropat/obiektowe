package com.example.zad08.ui.Koszyk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<String>> mCart;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        // Initialize cart
        mCart = new MutableLiveData<>();
        mCart.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<String>> getCart() {
        return mCart;
    }
}
