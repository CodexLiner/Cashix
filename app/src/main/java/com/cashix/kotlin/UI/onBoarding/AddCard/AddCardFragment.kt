package com.cashix.kotlin.UI.onBoarding.AddCard

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.databinding.FragmentAddCardBinding
import com.cashix.kotlin.UI.onBoarding.shared.AddNewCardRequest
import com.cashix.utils.SnakeBar
import dagger.hilt.android.AndroidEntryPoint

private const val BOOL = "callback"

@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private val viewModel: AddCardViewModel by viewModels()
    lateinit var binding: FragmentAddCardBinding
    private var cc: Boolean = false


    companion object {
        fun newInstance(boolean: Boolean) = AddCardFragment().apply {
            arguments = Bundle().apply {
                putBoolean(BOOL, boolean)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cc = it.getBoolean(BOOL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardNumber.addTextChangedListener {
            if (it.toString().length == 6) {
                viewModel.validateBIN(it.toString())
            } else if (it.toString().length < 6) {
                binding.cardSvg.startIconDrawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.card, requireContext().theme)
            }
        }

        binding.addNewCard.setOnClickListener {
            if (TextUtils.isEmpty(binding.cardNumber.text)) {
                SnakeBar(requireActivity()).showSnackbar("Card Number is Required")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.cardExpiryMonth.text)) {
                SnakeBar(requireActivity()).showSnackbar("Please enter expiry month")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.cardExpiryYear.text)) {
                SnakeBar(requireActivity()).showSnackbar("Please enter expiry month")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.cardCvv.text)) {
                SnakeBar(requireActivity()).showSnackbar("Cvv number is Required")
                return@setOnClickListener
            }
            viewModel.addCard(
                AddNewCardRequest(
                    "",
                    binding.cardNumber.text.toString(),
                    binding.cardExpiryMonth.text.toString(),
                    binding.cardExpiryYear.text.toString(),
                    binding.cardCvv.text.toString()
                )
            )
            next()
        }
    }

    private fun next() {
        viewModel.cardAddedResponse.observe(viewLifecycleOwner) {
            if (it.status.equals("success")) {
                SnakeBar(requireActivity()).showSnackbar("Card Successfully Added")
                requireActivity().supportFragmentManager.beginTransaction().remove(this@AddCardFragment).commit()
            }else SnakeBar(requireActivity()).showSnackbar(it.message)
        }
    }
}