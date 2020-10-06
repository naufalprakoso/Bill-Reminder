package com.naufalprakoso.billreminder.ui.bill.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.repository.BillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BillDetailViewModel @ViewModelInject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    fun updateBill(bill: Bill) {
        viewModelScope.launch {
            update(bill)
        }
    }

    private suspend fun update(bill: Bill) {
        withContext(Dispatchers.IO) {
            billRepository.update(bill)
        }
    }
}