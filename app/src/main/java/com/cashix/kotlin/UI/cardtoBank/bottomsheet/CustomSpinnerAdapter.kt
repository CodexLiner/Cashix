package com.cashix.kotlin.UI.cardtoBank.bottomsheet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.cashix.R
import com.cashix.kotlin.UI.onBoarding.shared.CardDetailsResponse

class CustomSpinnerAdapter(context: Context, resource: Int, items: ArrayList<CardDetailsResponse>) :
    ArrayAdapter<CardDetailsResponse>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false)

        val bank_name: TextView = itemView.findViewById(R.id.bank_name)
        val last_digit: TextView = itemView.findViewById(R.id.last_digit)
        val mask = "XXXX "

        bank_name.text = getItem(position)?.cardbank   ?: ""
        last_digit.text = (mask + getItem(position)?.cardnumber) ?: ""

        return itemView
    }
}
