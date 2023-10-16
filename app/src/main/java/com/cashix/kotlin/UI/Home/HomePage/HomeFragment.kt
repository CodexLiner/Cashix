package com.cashix.kotlin.UI.Home.HomePage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.UIFragments.BankTransfer
import com.cashix.UIFragments.MoreButton
import com.cashix.UIFragments.PayRent
import com.cashix.UIFragments.WalletTransfer
import com.cashix.adapters.TransactionAdapters
import com.cashix.adapters.models.TransactionModel
import com.cashix.databinding.FragmentHome2Binding
import com.cashix.utils.Binder.BindViews
import com.cashix.utils.change
import com.cashix.utils.changeHelper
import com.cashix.utils.common
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHome2Binding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHome2Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val change = change(changeHelper(requireActivity().supportFragmentManager, R.id.mainLayout))
        binding.BankTransfer.setOnClickListener { change.go(BankTransfer::class.java) }
        binding.payRent.setOnClickListener { change.go(PayRent::class.java) }
        binding.walletButton.setOnClickListener { change.go(WalletTransfer::class.java) }
        binding.moreButton.setOnClickListener { change.go(MoreButton::class.java) }
        binding.reaMore.setOnClickListener { common.Open(requireContext(), "") }


        val tList = ArrayList<TransactionModel>()
        tList.add(TransactionModel("id", "ad", "success", "500", "Bank Transfer", "dd", "dd"))
        tList.add(TransactionModel("id", "ad", "success", "100", "Wallet Transfer", "dd", "dd"))
        tList.add(TransactionModel("id", "ad", "success", "1582", "Credit Card", "dd", "dd"))
        tList.add(TransactionModel("id", "ad", "success", "69845", "Rent Pay", "dd", "dd"))
        val transactionAdapters = TransactionAdapters(tList, "")
        BindViews(requireContext()).setVerticalRecycler(
            binding.TransactionRecycler,
            transactionAdapters
        )
        binding.reaMore.setOnClickListener { common.Open(requireContext(), "") }
    }

}