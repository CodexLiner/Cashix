package com.cashix.UIFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.R;
import com.cashix.UI.Home_Activity;
import com.cashix.databinding.FragmentValidaterBinding;

public class validaterFragment extends Fragment {
    private FragmentValidaterBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public validaterFragment() {
        // Required empty public constructor
    }

    public static validaterFragment newInstance(String param1, String param2) {
        validaterFragment fragment = new validaterFragment();
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
        binding = FragmentValidaterBinding.inflate(inflater);
        binding.submitOtpButton.setOnClickListener((View v)->{
            startActivity(new Intent(requireContext() , Home_Activity.class));
        });
        return binding.getRoot();
    }
}