package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashix.databinding.FragmentItemListDialogListDialogBinding
import com.cashix.kotlin.UI.cardtoBank.shared.bottomSheetListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TOKEN = "secretKey"
private const val INTERFACE = "interface"

@AndroidEntryPoint
class ModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var listener: bottomSheetListener
    lateinit var binding: FragmentItemListDialogListDialogBinding
    private lateinit var paymentLauncher: PaymentLauncher
    lateinit var clientSecretKey: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false);
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"

        @JvmStatic
        fun newInstance(token: String, bottomSheetListener: bottomSheetListener) =
            ModalBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(TOKEN, token)
                    listener = bottomSheetListener
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clientSecretKey = it.getString(TOKEN, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.cardRecycler.adapter = cardsadapter()

        PaymentConfiguration.init(
            requireContext(),
            "pk_live_51JxaLNSBlkdvTJctb6Ke8yLG2u38p7Uy8kdhc9zk9ZukfWevAKXTSTdjhWOeelZTmj9L9d5cV1FeN3G8bIUQGPOo00Z7TSfXVz"
        );
        val paymentConfiguration = PaymentConfiguration.getInstance(requireContext())

        binding.cardInputWidget.setCardNumber("4341680200693892")
        binding.cardInputWidget.setExpiryDate(2, 2025)
        binding.cardInputWidget.setCvcCode("354")

        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )

        binding.payButtonSheet.setOnClickListener {
            startCheckout(binding.cardInputWidget.paymentMethodCreateParams, clientSecretKey)
        }
        binding.dismissSheet.setOnClickListener {
            listener.onBottomSheetDismissed()
            this@ModalBottomSheet.dismiss()
        }
    }

    private fun startCheckout(
        params: PaymentMethodCreateParams?,
        paymentIntentClientSecret: String,
    ) {
        params?.let {
            val confirmParams = ConfirmPaymentIntentParams
                .createWithPaymentMethodCreateParams(it, paymentIntentClientSecret)
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
        Log.d("TAG", "DaggerTest onPaymentResult: $message")
    }
}