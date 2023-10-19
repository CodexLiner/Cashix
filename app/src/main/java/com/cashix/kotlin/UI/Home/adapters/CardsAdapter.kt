package com.cashix.kotlin.UI.Home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cashix.R
import com.cashix.kotlin.UI.onBoarding.shared.CardDetailsResponse
import com.stripe.android.view.CardMultilineWidget

class CardsAdapter(cards: List<CardDetailsResponse>) : RecyclerView.Adapter<CardsAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val lastDigit: TextView = itemView.findViewById(R.id.last_digit)
        val bankLogo: ImageView = itemView.findViewById(R.id.bank_logo)
        val cardInputWidget: CardMultilineWidget = itemView.findViewById(R.id.cardInputWidget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notice_itemlist, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2;
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.cardInputWidget.setCardNumber("4341680200693892")
        holder.cardInputWidget.setExpiryDate(2, 2025)
        holder.cardInputWidget.setCvcCode("354")
    }

}