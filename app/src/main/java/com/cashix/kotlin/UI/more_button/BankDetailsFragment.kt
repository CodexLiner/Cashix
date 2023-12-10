package com.cashix.kotlin.UI.more_button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.databinding.FragmentBankDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankDetailsFragment : Fragment() {
    val viewModel: MoreViewModel by viewModels()
    lateinit var binding: FragmentBankDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBankDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.localBankDetails()
        next()

    }

    private fun next() {
        viewModel.bankDetails.observe(viewLifecycleOwner) {
            binding.bankAc.setText(it.accountnumber.toString())
            binding.bankIFSC.setText(it.bankifsc.toString())
            binding.bankName.setText(it.holdername.toString())
        }
    }

}