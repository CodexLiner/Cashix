package com.cashix.UIFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.databinding.FragmentLoginBinding;
import com.cashix.network.AsyncResponse;
import com.cashix.network.BackendCall;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements AsyncResponse {
    private BackendCall backendCall;
    private FragmentLoginBinding binding;
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
            if (common.isEmpty(binding.mobileText)){
                nextStep();
            }
        });
        return binding.getRoot();
    }

    private void nextStep() {
        Map<String , String> map = new HashMap<>();
        map.put("mobile" , binding.mobileText.getText().toString().trim());
        backendCall = new BackendCall(STATIC.LOGIN , map , "" , requireActivity());
        backendCall.asyncResponse = this;
        backendCall.execute();
    }

    @Override
    public void Result(JSONObject jsonObject){
        if (jsonObject!=null){
            Toast.makeText(requireContext(), "Otp Sent Successfully", Toast.LENGTH_SHORT).show();
            try {
                if (jsonObject.getString("lastToken")!=null){
                    new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.MainFrame))
                            .goWithParams(new ValidaterFragment(jsonObject.getString("lastToken") ,
                                    binding.mobileText.getText().toString()));
                }
            }catch (Exception ignored){}
        }
    }
}