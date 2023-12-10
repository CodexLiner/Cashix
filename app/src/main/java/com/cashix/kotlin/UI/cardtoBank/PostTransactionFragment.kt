package com.cashix.kotlin.UI.cardtoBank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cashix.R
import com.cashix.databinding.FragmentPostTransactionBinding

class PostTransactionFragment : Fragment() {
    lateinit var binding: FragmentPostTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostTransactionBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostTransactionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}