package com.cashix.kotlin.UI.cardtoBank

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cashix.databinding.FragmentCardToBankBinding
import com.cashix.utils.SnakeBar
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CardToBankFragment : Fragment() {


    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentLauncher: PaymentLauncher


    lateinit var binding: FragmentCardToBankBinding
    lateinit var paymentSheet: PaymentSheet
    private val viewModel: CardToBankViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCardToBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paymentConfiguration = PaymentConfiguration.getInstance(requireContext())
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
        binding.payButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.transferAmount.text)) {
                SnakeBar(requireActivity()).showSnackbar("Amount must not be empty")
                return@setOnClickListener
            }
            viewModel.getStripeIntent(
                (binding.transferAmount.text.toString().toInt() * 100).toString(), "transaction"
            )
            next()
        }
        binding.cardInputWidget.setCardNumber("4341680200693892")
        binding.cardInputWidget.setExpiryDate(2, 2025)
        binding.cardInputWidget.setCvcCode("354")

    }

    private fun next() {
        viewModel.stripeIntentResult.observe(viewLifecycleOwner) {
            if (it.status.equals("success")) {
                paymentIntentClientSecret = it.clientSecret
                startCheckout()
            }
        }
    }

    private fun startCheckout() {
        binding.cardInputWidget.paymentMethodCreateParams?.let { params ->
            val confirmParams = ConfirmPaymentIntentParams
                .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
            lifecycleScope.launch {
                paymentLauncher.confirm(confirmParams)
            }
        }
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {
        val message = when (paymentResult) {
            is PaymentResult.Completed -> {
                "Completed!"
            }
            is PaymentResult.Canceled -> {
                "Canceled!"
            }
            is PaymentResult.Failed -> {
                // This string comes from the PaymentIntent's error message.
                // See here: https://stripe.com/docs/api/payment_intents/object#payment_intent_object-last_payment_error-message
                "Failed: " + paymentResult.throwable.message
            }
        }
    }
}