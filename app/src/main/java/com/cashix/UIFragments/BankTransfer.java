package com.cashix.UIFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.databinding.FragmentBankTransferBinding;
import com.cashix.keyboard.custom_keyboard;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

public class BankTransfer extends Fragment {
    private FragmentBankTransferBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BankTransfer() {
        // Required empty public constructor
    }

    public static BankTransfer newInstance(String param1, String param2) {
        BankTransfer fragment = new BankTransfer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBankTransferBinding.inflate(inflater);
        InputConnection ic = binding.ammoutText.onCreateInputConnection(new EditorInfo());
        binding.customKeyboard.setInputConnection(ic);
        binding.swipePayButton.setEnabled(false);
        binding.ammoutText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.ammoutText.setTextIsSelectable(false);
        binding.ammoutText.setShowSoftInputOnFocus(false);
        binding.backButton.setOnClickListener(v -> { common.back(requireActivity());});
        binding.ammoutText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                common.editTextLength(s , binding.swipePayButton);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        binding.swipePayButton.setOnClickListener(v -> {
            startPayment();
        });
        return binding.getRoot();
    }

    private void startPayment() {
        Bundle bundle = new Bundle();
        String amount = binding.ammoutText.getText().toString().toString();
        if (TextUtils.isEmpty(amount) || Integer.parseInt(amount) < 1 ){
            binding.ammoutText.requestFocus();
            return;
        }
        int bundleAmount = Integer.parseInt(amount);
        bundle.putInt(STATIC.Amount , bundleAmount);
        bundle.putString(STATIC.TransactionType , "bank transfer");
        new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.MainFrame)).goWithParams(new CheckOutFragment(bundle));
    }
}