package com.raantech.mycups.data.di.daos

import com.raantech.mycups.data.daos.local.CartLocalDao
import com.raantech.mycups.data.db.ApplicationDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDaosModule {

    @Provides
    @Singleton
    fun provideCartLocalDao(
        applicationDB: ApplicationDB
    ): CartLocalDao {
        return applicationDB.cartLocalDao()
    }
}