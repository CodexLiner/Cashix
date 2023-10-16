package com.cashix.kotlin.UI.SignUi.VerifyOTP

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.UIFragments.HomeFragment
import com.cashix.databinding.FragmentVerifyBinding
import com.cashix.kotlin.UI.onBoarding.AddUser.CreateUserFragment
import com.cashix.utils.Bar
import com.cashix.utils.SnakeBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TOKEN = "token"
private const val MOBILE = "mobile"

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private val viewModel: VerifyViewModel by viewModels()
    lateinit var binding: FragmentVerifyBinding
    lateinit var token: String
    lateinit var mobile: String

    @Inject
    lateinit var loading: Bar

    companion object {
        @JvmStatic
        fun newInstance(mobile: String, token: String) = VerifyFragment().apply {
            arguments = Bundle().apply {
                putString(TOKEN, token)
                putString(MOBILE, mobile)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(TOKEN, "")
            mobile = it.getString(MOBILE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = Bar(requireContext())
        binding.firstPinView.setOnClickListener {
            binding.ScrollOtp.postDelayed({
                val lastChild: View =
                    binding.ScrollOtp.getChildAt(binding.ScrollOtp.getChildCount() - 1)
                val bottom: Int =
                    lastChild.bottom + binding.ScrollOtp.getPaddingBottom()
                val sy: Int = binding.ScrollOtp.getScrollY()
                val sh: Int = binding.ScrollOtp.getHeight()
                val delta = bottom - (sy + sh)
                binding.ScrollOtp.smoothScrollBy(0, delta)
            }, 200)
        }
        binding.sentMobile.text = mobile
        binding.submitOtpButton.setOnClickListener {
            binding.submitOtpButton.isEnabled = false
            loading.show()
            viewModel.updateToken(token)
            viewModel.validateOTP(binding.firstPinView.text.toString(), mobile)
            next()
        }
    }

    private fun next() {
        viewModel.sendVerifyOTPResponse.observe(viewLifecycleOwner) {
            if (it.status == "success") {
                if (it.oldUser) {
                    nextAdd(it.token)
                } else {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.mainLayout, CreateUserFragment.newInstance(it.token)).commit()
                    loading.show()
                }
            } else {
                binding.submitOtpButton.isEnabled = true
                loading.hide();
                SnakeBar(requireActivity()).showSnackbar(it.status)
            }
            SnakeBar(requireActivity()).showSnackbar("login success")
        }
    }

    private fun nextAdd(token: String) {
        viewModel.updateToken(token)
        viewModel.getUser(token)
        viewModel.sendUserAddedResponse.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.mainLayout, HomeFragment()).commit()
                loading.hide()
            }
        }
    }
}