package com.example.restaurantapp.ui.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.R;
import com.example.restaurantapp.backend.Account;
import com.example.restaurantapp.databinding.FragmentAccountBinding;
import com.example.restaurantapp.ui.ChangePasswordFragment;

import java.util.List;

public class AccountFragment extends Fragment {
    private Account account; // TODO: how to interact with Account?

    private EditText nick_name;
    private Button change_password;
    private Button favorites;
    private Button comments;
    private Button bookings;

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAccount;
        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                account.setNickName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getContext(), "Nickname updated!", Toast.LENGTH_SHORT).show();
            }
        });

        change_password = root.findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment dialog = new ChangePasswordFragment();
                dialog.show(requireActivity().getSupportFragmentManager(), "ChangePasswordDialog");
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> favoriteRestaurants = account.getFavoriteRestaurants();
                // TODO: same logic to generate list of restaurants as on SearchFragment

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