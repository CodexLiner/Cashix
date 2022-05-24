package com.cashix.UIFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.R;
import com.cashix.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private static FragmentLoginBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = FragmentLoginBinding.inflate(inflater);
        binding.sendOtpButton.setOnClickListener((View v)->{
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("Login")
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .setReorderingAllowed(true)
                    .add(R.id.MainFrame, validaterFragment.class, null)
                    .commit();
        });



        return binding.getRoot();
    }
}