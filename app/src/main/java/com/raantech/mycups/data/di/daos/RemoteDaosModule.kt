package com.raantech.mycups.data.di.daos

import com.raantech.mycups.data.daos.remote.cart.CartRemoteDao
import com.raantech.mycups.data.daos.remote.common.CommonRemoteDao
import com.raantech.mycups.data.daos.remote.configuration.ConfigurationRemoteDao
import com.raantech.mycups.data.daos.remote.lookups.LookupsRemoteDao
import com.raantech.mycups.data.daos.remote.media.MediaRemoteDao
import com.raantech.mycups.data.daos.remote.orders.OrdersRemoteDao
import com.raantech.mycups.data.daos.remote.product.ProductRemoteDao
import com.raantech.mycups.data.daos.remote.storage.StorageRemoteDao
import com.raantech.mycups.data.daos.remote.user.UserRemoteDao
import com.raantech.mycups.data.daos.remote.wishlist.WishListRemoteDao
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
    fun provideOrdersRemoteDao(
        retrofit: Retrofit
    ): OrdersRemoteDao {
        return retrofit.create(OrdersRemoteDao::class.java)
    }

    @Provides
    fun provideProductRemoteDao(retrofit: Retrofit): ProductRemoteDao =
        retrofit.create(ProductRemoteDao::class.java)

    @Singleton
    @Provides
    fun provideWishListRemoteDao(
        retrofit: Retrofit
    ): WishListRemoteDao {
        return retrofit.create(WishListRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideMediaRemoteDao(
        retrofit: Retrofit
    ): MediaRemoteDao {
        return retrofit.create(MediaRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideStorageRemoteDao(
        retrofit: Retrofit
    ): StorageRemoteDao {
        return retrofit.create(StorageRemoteDao::class.java)
    }

}