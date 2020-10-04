package com.naufalprakoso.billreminder.ui.main.all

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.repository.BillRepository

class BillViewModel @ViewModelInject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    fun getBills(): LiveData<List<Bill>> = billRepository.getBills()
}