package com.example.zad08.ui.Sklep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Map<String, String>> mProducts;
    private final MutableLiveData<List<String>> mCart;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        // Initialize product list
        mProducts = new MutableLiveData<>();
        Map<String, String> productList = new HashMap<>();
        productList.put("Laptop", "Elektronika");
        productList.put("Monitor", "Elektronika");
        productList.put("Autostopem przez galaktykę", "Książki");
        productList.put("Chleb", "Jedzenie");
        productList.put("Marionetka", "Zabawki");
        mProducts.setValue(productList);

        // Initialize cart
        mCart = new MutableLiveData<>();
        mCart.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Map<String, String>> getProducts() {
        return mProducts;
    }

    public LiveData<List<String>> getCart() {
        return mCart;
    }

    public void addToCart(String productName) {
        List<String> currentCart = mCart.getValue();
        if (currentCart != null) {
            currentCart.add(productName);
            mCart.setValue(currentCart);
        }
    }
}
