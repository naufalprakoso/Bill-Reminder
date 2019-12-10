package com.naufalprakoso.billreminder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.naufalprakoso.billreminder.database.entity.Bill

@Dao
interface BillDao {
    @Query("SELECT * FROM bills")
    fun getAll(): LiveData<List<Bill>>

    @Query("SELECT * FROM bills WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<Bill>>

    @Query("SELECT * FROM bills WHERE id = :id")
    fun getDetail(id: Int): Bill

    @Insert
    fun insertAll(vararg bill: Bill)

    @Delete
    fun delete(bill: Bill)

    @Update
    fun update(vararg bill: Bill)
}
