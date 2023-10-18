package com.cashix.kotlin.UI.onBoarding.AddCard

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.cashix.R
import com.cashix.databinding.FragmentAddCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private val viewModel: AddCardViewModel by viewModels()
    lateinit var binding: FragmentAddCardBinding

    companion object {
        fun newInstance() = AddCardFragment()
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
    }

}