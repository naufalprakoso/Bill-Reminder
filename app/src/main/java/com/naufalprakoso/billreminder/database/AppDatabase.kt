package com.naufalprakoso.billreminder.database

import android.content.Context
import androidx.room.*
import com.naufalprakoso.billreminder.database.dao.BillDao
import com.naufalprakoso.billreminder.database.entity.Bill

@Database(entities = [Bill::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "bill-reminder.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}