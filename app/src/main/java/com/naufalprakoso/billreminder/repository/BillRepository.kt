package com.naufalprakoso.billreminder.repository

import androidx.lifecycle.LiveData
import com.naufalprakoso.billreminder.database.dao.BillDao
import com.naufalprakoso.billreminder.database.entity.Bill
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface BillRepository {
    fun getBills(): LiveData<List<Bill>>
    fun getUnpaidBills(): LiveData<List<Bill>>
    fun getPaidBills(): LiveData<List<Bill>>
    fun update(bill: Bill)
    fun insert(bill: Bill)
}

class BillRepositoryImpl(
    private val billDao: BillDao,
    override val coroutineContext: CoroutineContext = Dispatchers.IO
): BillRepository, CoroutineScope {

    override fun getBills(): LiveData<List<Bill>> =
        billDao.getBills()

    override fun getUnpaidBills(): LiveData<List<Bill>> =
        billDao.getUnpaidBills()

    override fun getPaidBills(): LiveData<List<Bill>> =
        billDao.getPaidBills()

    override fun update(bill: Bill) {
        billDao.update(bill)
    }

    override fun insert(bill: Bill) {
        billDao.insert(bill)
    }

}