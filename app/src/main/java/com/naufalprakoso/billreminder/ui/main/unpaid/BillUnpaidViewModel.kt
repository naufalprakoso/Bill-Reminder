package com.naufalprakoso.billreminder.ui.main.unpaid

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.repository.BillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BillUnpaidViewModel @ViewModelInject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    fun getBills(): LiveData<List<Bill>> = billRepository.getUnpaidBills()

    fun updateBills(bill: Bill) {
        viewModelScope.launch {
            updateBill(bill)
            getBills()
        }
    }

    private suspend fun updateBill(bill: Bill) {
        withContext(Dispatchers.IO) {
            billRepository.update(bill)
        }
    }
}