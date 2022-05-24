package com.cashix.UIFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.R;
import com.cashix.UIFragments.more_button.BankDetails;
import com.cashix.UIFragments.more_button.Transactions;
import com.cashix.UIFragments.more_button.UserProfile;
import com.cashix.databinding.FragmentMoreButtonBinding;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreButton extends Fragment {
    private FragmentMoreButtonBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreButton() {
        // Required empty public constructor
    }
    public static MoreButton newInstance(String param1, String param2) {
        MoreButton fragment = new MoreButton();
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
       binding = FragmentMoreButtonBinding.inflate(inflater);
        change change = new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.MainFrame));
       binding.userDetails.setOnClickListener((v -> {
           change.go(UserProfile.class);
       }));
       binding.bankAccount.setOnClickListener((v -> {
           change.go(BankDetails.class);
       }));
       binding.mTransactions.setOnClickListener((v -> {
           change.go(Transactions.class);
       }));
       binding.mBillers.setOnClickListener((v -> {

       }));
       binding.helpSupport.setOnClickListener((v -> {

       }));
       binding.termCondtion.setOnClickListener((v -> {

       }));
       binding.privacyPolicy.setOnClickListener((v -> {

       }));
       binding.rateUs.setOnClickListener((v -> {
           try {
               Uri marketUri = Uri.parse("market://details?id=" + requireActivity().getPackageName());
               Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
               startActivity(marketIntent);
           }catch(ActivityNotFoundException e) {
               common.Open(requireContext() , "https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName());
           }
       }));
       binding.aboutUs.setOnClickListener((v -> {

       }));
       binding.backButton.setOnClickListener(v -> { common.back(requireActivity());});
        return binding.getRoot();
    }

}