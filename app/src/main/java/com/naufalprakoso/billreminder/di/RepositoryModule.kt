package com.naufalprakoso.billreminder.di

import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.repository.BillRepository
import com.naufalprakoso.billreminder.repository.BillRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideBillRepository(
        appDatabase: AppDatabase
    ): BillRepository {
        return BillRepositoryImpl(
            appDatabase.billDao()
        )
    }
}