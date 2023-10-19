package com.cashix.kotlin.UI.cardtoBank.shared

import android.os.Parcel
import android.os.Parcelable

interface bottomSheetListener {
    fun onBottomSheetDismissed(message: String = "dismissed")
}

class MyInterfaceImplementation() : Parcelable, bottomSheetListener {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyInterfaceImplementation> {
        override fun createFromParcel(parcel: Parcel): MyInterfaceImplementation {
            return MyInterfaceImplementation(parcel)
        }

        override fun newArray(size: Int): Array<MyInterfaceImplementation?> {
            return arrayOfNulls(size)
        }
    }

    override fun onBottomSheetDismissed(message: String) {
    }

}