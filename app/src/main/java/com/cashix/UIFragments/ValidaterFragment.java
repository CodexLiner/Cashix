package com.cashix.UIFragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.UI.HomeActivity;
import com.cashix.constants.STATIC;
import com.cashix.database.user.UserInfo;
import com.cashix.databinding.FragmentValidaterBinding;
import com.cashix.network.AsyncResponse;
import com.cashix.network.BackendCall;
import com.cashix.utils.common;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ValidaterFragment extends Fragment implements AsyncResponse {
    private FragmentValidaterBinding binding;
    private BackendCall backendCall;
    private String token;
    private String mobile;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ValidaterFragment(String token , String mobile) {
        this.token = token;
        this.mobile = mobile;
    }
    public ValidaterFragment(){}

    public static ValidaterFragment newInstance(String param1, String param2) {
        ValidaterFragment fragment = new ValidaterFragment();
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
         if (common.isEmpty(binding.firstPinView)){
             validateOtp(token , Objects.requireNonNull(binding.firstPinView.getText()).toString());

         }
        });
        return binding.getRoot();
    }

    private void validateOtp(String token, String otp) {
        Map<String , String> map= new HashMap<>();
        map.put("code" , otp);
        map.put("mobile" , mobile);
        backendCall = new BackendCall(STATIC.VERIFYOTP, map, token , requireActivity());
        backendCall.asyncResponse = this;
        backendCall.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void Result(JSONObject jsonObject) {
        if (jsonObject!=null){
            try {
                Log.d("TAG", "doInBackground d: "+jsonObject.getString("token"));
                new UserInfo(requireContext()).getDB().setUser("9399846909" , jsonObject.getString("token") , 1);
                startActivity(new Intent(requireContext() , HomeActivity.class));
            }catch (Exception ignored){}
        }
    }
}