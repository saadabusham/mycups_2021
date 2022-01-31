package com.technzone.bai3.data.di.daos

import com.technzone.bai3.data.daos.remote.adresses.AddressRemoteDao
import com.technzone.bai3.data.daos.remote.cart.CartRemoteDao
import com.technzone.bai3.data.daos.remote.common.CommonRemoteDao
import com.technzone.bai3.data.daos.remote.configuration.ConfigurationRemoteDao
import com.technzone.bai3.data.daos.remote.favorites.FavoritesRemoteDao
import com.technzone.bai3.data.daos.remote.lookups.LookupsRemoteDao
import com.technzone.bai3.data.daos.remote.orders.OrdersRemoteDao
import com.technzone.bai3.data.daos.remote.user.UserRemoteDao
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

    @Singleton
    @Provides
    fun provideCommonRemoteDao(
        retrofit: Retrofit
    ): CommonRemoteDao {
        return retrofit.create(CommonRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideCartRemoteDao(
        retrofit: Retrofit
    ): CartRemoteDao {
        return retrofit.create(CartRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideAddressRemoteDao(
        retrofit: Retrofit
    ): AddressRemoteDao {
        return retrofit.create(AddressRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideOrdersRemoteDao(
        retrofit: Retrofit
    ): OrdersRemoteDao {
        return retrofit.create(OrdersRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideFavoritesRemoteDao(
        retrofit: Retrofit
    ): FavoritesRemoteDao {
        return retrofit.create(FavoritesRemoteDao::class.java)
    }

}