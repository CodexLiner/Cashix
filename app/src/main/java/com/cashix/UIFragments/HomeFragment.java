package com.cashix.UIFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.R;
import com.cashix.adapters.noticeAdapter;
import com.cashix.adapters.noticeModel;
import com.cashix.databinding.FragmentHomeBinding;
import com.cashix.utils.Binder.BindViews;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static FragmentHomeBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView.LayoutManager  noticLayoutManager;
    noticeAdapter noticeAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
      binding = FragmentHomeBinding.inflate(inflater);
      binding.backButton.setOnClickListener(v -> { common.back(requireActivity());});
        change change = new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.MainFrame));

      binding.BankTransfer.setOnClickListener((View bank)->{
          change.go(BankTransfer.class);
      });
      binding.payRent.setOnClickListener((View rent)->{
          change.go(PayRent.class);
      });
      binding.walletButton.setOnClickListener((View wallet)->{
          change.go(WalletTransfer.class);
      });
      binding.moreButton.setOnClickListener((View more)->{
          change.go(MoreButton.class);
      });
//      rec test
        ArrayList<noticeModel> Notlist = new ArrayList<>();
        Notlist.add(
                new noticeModel(
                        "https://www.torrancelearning.com/wp-content/uploads/2019/10/FLIP-hero-banner-1600-X-500-v2.jpg"));
        Notlist.add(
                new noticeModel(
                        "https://indiahikes.com/wp-content/uploads/2016/05/Hampta-Pass-Banner-1600-x-500.jpg"));
        Notlist.add(
                new noticeModel(
                        "https://images.unsplash.com/photo-1506782081254-09bcfd996fd6?ixlib=rb-1.2.1&auto=format&fit=crop&w=1600&h=500&q=60.jpg"));
        noticeAdapter = new noticeAdapter(Notlist);
        new BindViews(requireContext()).setHorizontalRecycler(binding.NoticeRecycler , noticeAdapter);
        binding.reaMore.setOnClickListener(v -> {
            common.Open(requireContext() , "");
        });
        return binding.getRoot();
    }
}