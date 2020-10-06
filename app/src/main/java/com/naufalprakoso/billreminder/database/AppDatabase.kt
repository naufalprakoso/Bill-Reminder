package com.naufalprakoso.billreminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naufalprakoso.billreminder.database.dao.BillDao
import com.naufalprakoso.billreminder.database.entity.Bill

@Database(entities = [Bill::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

    companion object {
        private const val databaseName = "bill-reminder.db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                databaseName
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}