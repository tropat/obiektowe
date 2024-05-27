package com.example.zad08.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<String>> cart;

    public SharedViewModel() {
        cart = new MutableLiveData<>();
        cart.setValue(new ArrayList<>());
    }

    public LiveData<List<String>> getCart() {
        return cart;
    }

    public void addToCart(String productName) {
        List<String> currentCart = cart.getValue();
        if (currentCart != null) {
            currentCart.add(productName);
            cart.setValue(currentCart);
        }
    }
}
