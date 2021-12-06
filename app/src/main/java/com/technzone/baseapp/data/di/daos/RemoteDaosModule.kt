package com.technzone.baseapp.data.di.daos

import com.technzone.baseapp.data.daos.remote.configuration.ConfigurationRemoteDao
import com.technzone.baseapp.data.daos.remote.lookups.LookupsRemoteDao
import com.technzone.baseapp.data.daos.remote.user.UserRemoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDaosModule {


    @Singleton
    @Provides
    fun provideUserRemoteDao(
        retrofit: Retrofit
    ): UserRemoteDao {
        return retrofit.create(UserRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideConfigurationRemoteDao(
        retrofit: Retrofit
    ): ConfigurationRemoteDao {
        return retrofit.create(ConfigurationRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideLookUpsRemoteDao(
        retrofit: Retrofit
    ): LookupsRemoteDao {
        return retrofit.create(LookupsRemoteDao::class.java)
    }

}