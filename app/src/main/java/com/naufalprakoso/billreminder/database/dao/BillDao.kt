package com.naufalprakoso.billreminder.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import androidx.room.OnConflictStrategy
import com.naufalprakoso.billreminder.database.entity.Bill

@Dao
interface BillDao {
    @Query("SELECT * FROM bills where paid = 'false'")
    fun getUnpaidBills(): List<Bill>

    @Query("SELECT * FROM bills where paid = 'true'")
    fun getPaidBills(): List<Bill>

    @Query("SELECT * FROM bills")
    fun getBills(): List<Bill>

    @Query("SELECT * FROM bills WHERE title LIKE :title")
    fun getByTitle(title: String): List<Bill>

    @Query("SELECT * FROM bills WHERE id = :id")
    fun getBill(id: Int): Bill

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill)

    @Delete
    fun delete(bill: Bill)

    @Update
    fun update(bill: Bill)
}
