package com.naufalprakoso.billreminder.database.dao

import androidx.room.*
import com.naufalprakoso.billreminder.database.entity.Bill

@Dao
interface BillDao {
    @Query("SELECT * FROM bills where paid = 'false'")
    fun getBillsUnpaid(): List<Bill>

    @Query("SELECT * FROM bills where paid = 'true'")
    fun getBillsPaid(): List<Bill>

    @Query("SELECT * FROM bills")
    fun getBills(): List<Bill>

    @Query("SELECT * FROM bills WHERE title LIKE :title")
    fun findByTitle(title: String): List<Bill>

    @Query("SELECT * FROM bills WHERE id = :id")
    fun getDetail(id: Int): Bill

    @Insert
    fun insert(vararg bill: Bill)

    @Delete
    fun delete(bill: Bill)

    @Update
    fun update(vararg bill: Bill)
}
