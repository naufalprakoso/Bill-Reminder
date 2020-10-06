package com.naufalprakoso.billreminder.ui.bill.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.repository.BillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddBillViewModel @ViewModelInject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    fun insertBill(bill: Bill) {
        viewModelScope.launch {
            insert(bill)
        }
    }

    private suspend fun insert(bill: Bill) {
        withContext(Dispatchers.IO) {
            billRepository.insert(bill)
        }
    }
}