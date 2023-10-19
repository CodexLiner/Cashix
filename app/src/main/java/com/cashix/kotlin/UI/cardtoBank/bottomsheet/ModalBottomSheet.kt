package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashix.R
import com.cashix.databinding.FragmentItemListDialogListDialogBinding
import com.cashix.databinding.FragmentItemListDialogListDialogItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: FragmentItemListDialogListDialogBinding

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.cardRecycler.adapter = cardsadapter()
    }
}