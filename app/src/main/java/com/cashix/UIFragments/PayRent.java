package com.cashix.UIFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cashix.R;
import com.cashix.databinding.FragmentPayRentBinding;
import com.cashix.utils.Toaster;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

public class PayRent extends Fragment {
    private FragmentPayRentBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayRent() {}

    public static PayRent newInstance(String param1, String param2) {
        PayRent fragment = new PayRent();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayRentBinding.inflate(inflater);
        binding.backButton.setOnClickListener(v -> common.back(requireActivity()));
        binding.addAccountButton.setOnClickListener((View)->{
            if ((common.isEmpty(binding.holderAmount) &&
                 common.isEmpty(binding.holderName)) &&
                 common.isEmpty(binding.holderIFSC) &&
                 common.isEmpty(binding.holderAccount)){
                 startPayment();
            }
        });
        return binding.getRoot();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startPayment() {
        Bundle bundle = new Bundle();
        bundle.putString("uName" , binding.holderName.getText().toString().trim());
        bundle.putString("uIfsc" ,  binding.holderIFSC.getText().toString().trim());
        bundle.putString("uAccount" , binding.holderAccount.getText().toString().trim());
        bundle.putString("uDescription" ,  binding.holderReason.getText().toString().trim());
        bundle.putBoolean("rentType" , true);
        bundle.putInt("amount" , Integer.parseInt( binding.holderAmount.getText().toString().trim()));
        new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.mainLayout)).goWithParams(new CheckOutFragment(bundle));
    }
}