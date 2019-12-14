package com.naufalprakoso.billreminder.database

import android.content.Context
import androidx.room.*
import com.naufalprakoso.billreminder.database.dao.BillDao
import com.naufalprakoso.billreminder.database.entity.Bill

@Database(entities = [Bill::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val sLock = Object()

        fun getInstance(context: Context): AppDatabase? {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "bill-reminder.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}