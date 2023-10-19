package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cashix.R

class cardsadapter : RecyclerView.Adapter<cardsadapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2;
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

}