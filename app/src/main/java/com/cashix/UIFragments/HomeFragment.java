package com.cashix.UIFragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashix.R;
import com.cashix.adapters.TransactionAdapters;
import com.cashix.adapters.models.TransactionModel;
import com.cashix.adapters.noticeAdapter;
import com.cashix.adapters.models.noticeModel;
import com.cashix.databinding.FragmentHomeBinding;
import com.cashix.receivers.connection;
import com.cashix.utils.Binder.BindViews;
import com.cashix.utils.change;
import com.cashix.utils.changeHelper;
import com.cashix.utils.common;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    noticeAdapter noticeAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {}
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
      change change = new change(new changeHelper(requireActivity().getSupportFragmentManager() , R.id.mainLayout));
      checkConnection();
      binding.BankTransfer.setOnClickListener(v -> change.go(BankTransfer.class));
      binding.payRent.setOnClickListener(v -> change.go(PayRent.class) );
      binding.walletButton.setOnClickListener(v -> change.go(WalletTransfer.class));
      binding.moreButton.setOnClickListener(v -> change.go(MoreButton.class));
//      rec test
        ArrayList<noticeModel> Notlist = new ArrayList<>();
        Notlist.add(new noticeModel("https://img.indiefolio.com/fit-in/1100x0/filters:format(webp):fill(transparent)/project/body/316b2bde72bfcb43d7bdfd709b5ac4b4.jpg"));
        Notlist.add(new noticeModel("https://rexsilentium.com/wp-content/uploads/2019/07/cropped-New-1600x500-website-banner-3.png"));
        Notlist.add(new noticeModel("https://www.kpis.in/assets/img/costom-web-banner-img.png"));
        Notlist.add(new noticeModel("https://static.vecteezy.com/ti/vetor-gratis/p1/344730-web-design-e-banner-de-desenvolvimento-computador-com-ferramentas-e-site-de-construtor-ilustracaoial-plana-vetor.jpg"));
        noticeAdapter = new noticeAdapter(Notlist);
        new BindViews(requireContext()).setHorizontalRecycler(binding.NoticeRecycler , noticeAdapter);

        ArrayList<TransactionModel> tList = new ArrayList<>();
        tList.add(new TransactionModel("id" , "ad" , "success" , "500", "Bank Transfer" , "dd" , "dd"));
        tList.add(new TransactionModel("id" , "ad" , "success" , "100", "Wallet Transfer" , "dd" , "dd"));
        tList.add(new TransactionModel("id" , "ad" , "success" , "1582", "Credit Card" , "dd" , "dd"));
        tList.add(new TransactionModel("id" , "ad" , "success" , "69845", "Rent Pay" , "dd" , "dd"));
        TransactionAdapters transactionAdapters = new TransactionAdapters(tList , "");
        new BindViews(requireContext()).setVerticalRecycler(binding.TransactionRecycler , transactionAdapters);
        binding.reaMore.setOnClickListener(v -> common.Open(requireContext() , ""));
        return binding.getRoot();
    }

    private void readMore() {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setUrlBarHidingEnabled(true);
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(requireContext(), Uri.parse("https://coinswitch.co/kuber-terms"));
    }

    private void checkConnection() {
        connection conn = new connection();
        conn.onReceive(requireContext(), null);
    }

}