package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashix.R
import com.cashix.databinding.FragmentItemListDialogListDialogBinding
import com.cashix.kotlin.UI.cardtoBank.shared.bottomSheetListener
import com.cashix.kotlin.UI.onBoarding.shared.CardDetailsResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TOKEN = "secretKey"
private const val AMOUNT = "amount"
private const val INTERFACE = "interface"

@AndroidEntryPoint
class ModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var listener: bottomSheetListener
    lateinit var binding: FragmentItemListDialogListDialogBinding
    private lateinit var paymentLauncher: PaymentLauncher
    lateinit var clientSecretKey: String
    lateinit var mAmount: String


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
        fun newInstance(token: String, amount: String, bottomSheetListener: bottomSheetListener) =
            ModalBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(AMOUNT, amount)
                    listener = bottomSheetListener
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clientSecretKey = it.getString(TOKEN, "")
            mAmount = it.getString(AMOUNT, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardRecycler.layoutManager = LinearLayoutManager(requireContext())
        val list: ArrayList<CardDetailsResponse> = arrayListOf()

        binding.ogAmount.text = mAmount
        binding.finalAmount.text = finalAmount(mAmount)
        list.add(CardDetailsResponse("Gopal Meena", "HDFC Bank", "master card", "2532", true))
        list.add(CardDetailsResponse("Kamlesh Meena", "HDFC Bank", "master card", "3892", false))
        list.add(CardDetailsResponse("Gopal Meena", "HDFC Bank", "master card", "8602", false))
        binding.cardRecycler.adapter = CardsAdapter(list)

        val adapter = CustomSpinnerAdapter(requireContext(), R.layout.fragment_item_list_dialog_list_dialog_item, list)

        adapter.setDropDownViewResource(android.R.layout.activity_list_item)

        binding.spinner.adapter = adapter





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

    private fun finalAmount(mAmount: String?): CharSequence? {
        var og = mAmount?.toInt();
        og = og?.plus(90);
        return og?.toString()
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
        val alert = Dialog(requireContext())
        alert.setContentView(R.layout.info_dialog)
        alert.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        alert.window!!.setBackgroundDrawable(null)

        val text: TextView = alert.findViewById(R.id.dialogText);
        alert.findViewById<Button>(R.id.buttonConfirm).setOnClickListener {
            alert.dismiss()
        }
        val message = when (paymentResult) {
            is PaymentResult.Completed -> {
                text.text = "Completed!"
            }
            is PaymentResult.Canceled -> {
                text.text = "Canceled!"
            }
            is PaymentResult.Failed -> {
                text.text = "Failed: " + paymentResult.throwable.message
            }
        }
        alert.show();

        Log.d("TAG", "DaggerTest onPaymentResult: $message")
    }
}