package com.cashix.kotlin.UI.onBoarding.AddBank

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.databinding.FragmentAddBankBinding
import com.cashix.kotlin.UI.Home.HomePage.HomeFragment
import com.cashix.kotlin.UI.SignUi.VerifyOTP.VerifyFragment
import com.cashix.kotlin.UI.onBoarding.shared.AddBankRequest
import com.cashix.utils.Bar
import com.cashix.utils.SnakeBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddBankFragment : Fragment() {
    private val viewModel: AddBankViewModel by viewModels()
    lateinit var binding: FragmentAddBankBinding

    @Inject
    lateinit var loading: Bar

    companion object {
        fun newInstance() = AddBankFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loading = Bar(requireContext());
        binding = FragmentAddBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skipButton.setOnClickListener {
            home()
        }
        binding.addBank.setOnClickListener {
            if (TextUtils.isEmpty(binding.holderName.text)) {
                SnakeBar(requireActivity()).showSnackbar("Name is Required")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.bankname.text)) {
                SnakeBar(requireActivity()).showSnackbar("Bank name is Required")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.bankIFSC.text)) {
                SnakeBar(requireActivity()).showSnackbar("Ifsc is Required")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.accountNumber.text)) {
                SnakeBar(requireActivity()).showSnackbar("Account number is Required")
                return@setOnClickListener
            }
            loading.show()
            viewModel.addBankAccount(
                AddBankRequest(
                    binding.holderName.text.toString(),
                    binding.bankname.text.toString(),
                    binding.bankIFSC.text.toString(),
                    binding.accountNumber.text.toString()
                )
            );
            next()
        }
    }

    private fun home() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.mainLayout, HomeFragment())
            .commit()
    }

    private fun next() {
        viewModel.addBankResponse.observe(viewLifecycleOwner) {
            if (it.status.equals("success")) {
                SnakeBar(requireActivity()).showSnackbar("Bank Account Added")
                viewModel.addBankToLocalDatabase(
                    it.accounts.holdername,
                    it.accounts.bankname,
                    it.accounts.accountnumber,
                    it.accounts.bankifsc,
                )
                home()
            } else SnakeBar(requireActivity()).showSnackbar("Something went wrong try later")
            loading.hide()
        }
    }
}