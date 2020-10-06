package com.naufalprakoso.billreminder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.naufalprakoso.billreminder.database.entity.Bill

@Dao
interface BillDao {
    @Query("SELECT * FROM bills where paid = 'false'")
    fun getUnpaidBills(): LiveData<List<Bill>>

    @Query("SELECT * FROM bills where paid = 'true'")
    fun getPaidBills(): LiveData<List<Bill>>

    @Query("SELECT * FROM bills")
    fun getBills(): LiveData<List<Bill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill)

    @Update
    fun update(bill: Bill)
}
