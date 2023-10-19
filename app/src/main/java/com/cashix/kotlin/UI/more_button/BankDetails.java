package com.cashix.kotlin.UI.more_button;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.databinding.FragmentBankDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankDetails extends Fragment {
    private FragmentBankDetailsBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BankDetails() {
        // Required empty public constructor
    }
    public static BankDetails newInstance(String param1, String param2) {
        BankDetails fragment = new BankDetails();
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
        binding = FragmentBankDetailsBinding.inflate(inflater);
        return binding.getRoot();
    }
}