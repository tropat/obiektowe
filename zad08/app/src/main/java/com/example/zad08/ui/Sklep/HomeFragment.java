package com.example.zad08.ui.Sklep;

import com.example.zad08.R;
import com.example.zad08.ui.SharedViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zad08.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listView;
        homeViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> products) {
                List<Map<String, String>> productList = new ArrayList<>();
                for (Map.Entry<String, String> entry : products.entrySet()) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", entry.getKey());
                    item.put("category", entry.getValue());
                    productList.add(item);
                }

                SimpleAdapter adapter = new SimpleAdapter(getContext(), productList, R.layout.item_product,
                        new String[]{"name", "category"},
                        new int[]{R.id.product_name, R.id.product_category}) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        Button addButton = view.findViewById(R.id.add_to_cart_button);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String productName = productList.get(position).get("name");
                                sharedViewModel.addToCart(productName);
                                Toast.makeText(getContext(), "Dodano do koszyka: " + productName, Toast.LENGTH_SHORT).show();
                            }
                        });

                        return view;
                    }
                };

                listView.setAdapter(adapter);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
