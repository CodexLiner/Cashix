package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cashix.R
import com.cashix.kotlin.UI.onBoarding.shared.CardDetailsResponse
import com.stripe.android.view.CardMultilineWidget

private var checkPosition = 0;

class CardsAdapter(val list: ArrayList<CardDetailsResponse>) :
    RecyclerView.Adapter<CardsAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val radioButton = itemView.findViewById<RadioButton>(R.id.radio_button)
        val radioGroup = itemView.findViewById<RadioGroup>(R.id.mainGroup)
        val bankName = itemView.findViewById<TextView>(R.id.bankName)
        val lastDigit = itemView.findViewById<TextView>(R.id.last_digit)
        val bankLogo = itemView.findViewById<ImageView>(R.id.bank_logo)
        val cardInputWidget = itemView.findViewById<CardMultilineWidget>(R.id.cardInputWidget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val card = list[position]
        holder.radioGroup.setOnClickListener {
            if (!card.isSelected) {
                changeSelectionToTrue(list, position)
            }else{
                list[position].isSelected = true
            }
        }
//        holder.radioButton.isChecked = list[position].isSelected

        holder.cardInputWidget.setCardNumber("4341680200693892")
        holder.cardInputWidget.setExpiryDate(2, 2025)
        holder.cardInputWidget.setCvcCode("354")
    }

    private fun changeSelectionToTrue(list: ArrayList<CardDetailsResponse>, selectedPosition: Int) {
        for (index in list.indices) {
            list[index].isSelected = index == selectedPosition
            notifyDataSetChanged()
        }
    }

}