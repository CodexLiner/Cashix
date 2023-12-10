package com.cashix.kotlin.UI.cardtoBank

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.databinding.FragmentCardToBankBinding
import com.cashix.kotlin.UI.cardtoBank.bottomsheet.ModalBottomSheet
import com.cashix.kotlin.UI.cardtoBank.shared.bottomSheetListener
import com.cashix.utils.Bar
import com.cashix.utils.SnakeBar
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardToBankFragment : Fragment(), bottomSheetListener {


    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentLauncher: PaymentLauncher
    lateinit var loading: Bar
    lateinit var binding: FragmentCardToBankBinding
    lateinit var paymentSheet: PaymentSheet
    private val viewModel: CardToBankViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loading = Bar(requireContext())
        binding = FragmentCardToBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.payButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.transferAmount.text)) {
                SnakeBar(requireActivity()).showSnackbar("Amount must not be empty")
                return@setOnClickListener
            }
            binding.payButton.isEnabled = false
            loading.show()
            viewModel.getStripeIntent(
                (binding.transferAmount.text.toString().toInt() * 100).toString(), "transaction"
            )
            next()
        }


        viewModel.getStripeIntent(
            (binding.transferAmount.text.toString().toInt() * 100).toString(), "transaction"
        )
        next()
    }

    private var modalBottomSheet: ModalBottomSheet? = null
    private fun next() {
        viewModel.stripeIntentResult.observe(viewLifecycleOwner) { result ->
            if (result.status == "success" && viewModel.isNotLoading) {
                viewModel.isNotLoading = !viewModel.isNotLoading
                paymentIntentClientSecret = result.clientSecret
                modalBottomSheet = ModalBottomSheet.newInstance(paymentIntentClientSecret  , binding.transferAmount.text.toString(), this)
                modalBottomSheet?.isCancelable = false
                modalBottomSheet?.show(childFragmentManager, ModalBottomSheet.TAG)
            }
            loading.hide()
        }
    }

    override fun onBottomSheetDismissed(message: String) {
        binding.payButton.isEnabled = !binding.payButton.isEnabled
    }
}