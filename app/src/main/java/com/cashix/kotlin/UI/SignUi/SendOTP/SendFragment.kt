package com.cashix.kotlin.UI.SignUi.SendOTP

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.databinding.FragmentSendBinding
import com.cashix.kotlin.UI.SignUi.VerifyOTP.VerifyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendFragment : Fragment() {
    lateinit var binding: FragmentSendBinding
    private val viewModel: SendViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mobileText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.sendOtpButton.isEnabled = s.length == 10
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.sendOtpButton.setOnClickListener {
            viewModel.sendOtp(binding.mobileText.text.trim().toString())
            next()
        }
    }
    private fun next() {
        viewModel.otpResponseMutableLiveData.observe(viewLifecycleOwner) {
            if (it.status == "success") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack("sendOTP")
                    .replace(R.id.mainLayout, VerifyFragment.newInstance(it.mobile, it.token))
                    .commit()
            }
        }
    }

}