package com.cashix.kotlin.UI.onBoarding.AddCard

import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(repo: BoardRepository) : ViewModel() {

}