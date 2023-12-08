package com.cashix.kotlin.UI.Home.HomePage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashix.R
import com.cashix.UIFragments.MoreButton
import com.cashix.UIFragments.PayRent
import com.cashix.UIFragments.WalletTransfer
import com.cashix.adapters.TransactionAdapters
import com.cashix.adapters.models.TransactionModel
import com.cashix.databinding.FragmentHome2Binding
import com.cashix.kotlin.UI.Home.adapters.CardsAdapter
import com.cashix.kotlin.UI.cardtoBank.CardToBankFragment
import com.cashix.kotlin.UI.onBoarding.AddCard.AddCardFragment
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
        getCards()
        val change = change(changeHelper(requireActivity().supportFragmentManager, R.id.mainLayout))
        binding.BankTransfer.setOnClickListener { change.go(CardToBankFragment::class.java) }
        binding.payRent.setOnClickListener { change.go(PayRent::class.java) }
        binding.walletButton.setOnClickListener { change.go(WalletTransfer::class.java) }
        binding.moreButton.setOnClickListener { change.go(MoreButton::class.java) }
        binding.reaMore.setOnClickListener { common.Open(requireContext(), "") }
        binding.addNewCard.setOnClickListener { change.go(AddCardFragment::class.java) }


        val tList = ArrayList<TransactionModel>()
        tList.add(TransactionModel("id", "ad", "pending", "500", "Bank Transfer", "dd", "from hdfc card"))
        tList.add(TransactionModel("id", "ad", "success", "100", "Wallet Transfer", "dd", "home rent paid"))
        tList.add(TransactionModel("id", "ad", "failed", "1582", "Credit Card", "dd", "demo transaction"))
        tList.add(TransactionModel("id", "ad", "success", "69845", "Rent Pay", "dd", "dd"))
        val transactionAdapters = TransactionAdapters(tList, "")
        BindViews(requireContext()).setVerticalRecycler(
            binding.TransactionRecycler,
            transactionAdapters
        )
        binding.reaMore.setOnClickListener { common.Open(requireContext(), "") }
        binding.addNewCard.setOnClickListener {
            addNewCard();
        }
    }

    private fun getCards() {
        viewModel.getCreditCardData();
        viewModel.creditCardData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.messageAtHome.visibility = View.GONE
            }
            binding.CardsRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = CardsAdapter(it)
            }
        }
    }

    private fun addNewCard() {
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.fade_in, R.anim.fade_out
        ).addToBackStack(AddCardFragment.toString()).replace(R.id.mainLayout, AddCardFragment.newInstance(true))
            .commit()
    }
}