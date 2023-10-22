package com.cashix.kotlin.UI.onBoarding.AddBank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.database.DatabaseProvider
import com.cashix.database.bank.bankDataModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddBankRequest
import com.cashix.kotlin.UI.onBoarding.shared.AddBankResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBankViewModel @Inject constructor(
    val repo: BoardRepository,
    val databaseProvider: DatabaseProvider,
) : ViewModel() {
    val addBankResponse: MutableLiveData<AddBankResponse> = MutableLiveData()
    fun addBankAccount(body: AddBankRequest) {
        SafeApiRequest.safe {
            repo.addBankAccount(body).let {
                addBankResponse.postValue(it)
            }
        }
    }

    fun addBankToLocalDatabase(
        holdername: String,
        bankname: String,
        accountNumber: String,
        ifsc: String,
    ) {
        databaseProvider.getBankDB()
            .insertNewAccount(
                bankDataModel(
                    holdername,
                    bankname,
                    ifsc,
                    accountNumber,
                    databaseProvider.getUserDB().getUser(1).mobile
                )
            )
    }
}