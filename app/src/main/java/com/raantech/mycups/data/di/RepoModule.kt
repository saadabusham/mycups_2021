package com.raantech.mycups.data.di


import com.raantech.mycups.data.repos.address.AddressRepo
import com.raantech.mycups.data.repos.address.AddressRepoImp
import com.raantech.mycups.data.repos.cart.CartRepo
import com.raantech.mycups.data.repos.cart.CartRepoImp
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.common.CommonRepoImp
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.data.repos.user.UserRepoImp
import com.raantech.mycups.data.repos.configuration.ConfigurationRepo
import com.raantech.mycups.data.repos.configuration.ConfigurationRepoImp
import com.raantech.mycups.data.repos.favorites.FavoritesRepo
import com.raantech.mycups.data.repos.favorites.FavoritesRepoImp
import com.raantech.mycups.data.repos.lookups.LookupsRepo
import com.raantech.mycups.data.repos.lookups.LookupsRepoImp
import com.raantech.mycups.data.repos.media.MediaRepo
import com.raantech.mycups.data.repos.media.MediaRepoImp
import com.raantech.mycups.data.repos.orders.OrdersRepo
import com.raantech.mycups.data.repos.orders.OrdersRepoImp
import com.raantech.mycups.data.repos.product.ProductRepo
import com.raantech.mycups.data.repos.product.ProductRepoImpl
import com.raantech.mycups.data.repos.storage.StorageRepo
import com.raantech.mycups.data.repos.storage.StorageRepoImp
import com.raantech.mycups.data.repos.wishlist.WishListRepo
import com.raantech.mycups.data.repos.wishlist.WishListRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindConfigurationRepo(configurationRepoImp: ConfigurationRepoImp): ConfigurationRepo

    @Singleton
    @Binds
    abstract fun bindUserRepo(userRepoImp: UserRepoImp): UserRepo

    @Singleton
    @Binds
    abstract fun bindLookUpsRepo(lookupsRepoImp: LookupsRepoImp): LookupsRepo

    @Singleton
    @Binds
    abstract fun bindCommonRepo(commonRepoImp: CommonRepoImp): CommonRepo

    @Singleton
    @Binds
    abstract fun bindCartRepo(cartRepoImp: CartRepoImp): CartRepo

    @Singleton
    @Binds
    abstract fun bindAddressRepo(addressRepoImp: AddressRepoImp): AddressRepo

    @Singleton
    @Binds
    abstract fun bindOrdersRepo(ordersRepoImp: OrdersRepoImp): OrdersRepo

    @Singleton
    @Binds
    abstract fun bindFavoritesRepo(favoritesRepoImp: FavoritesRepoImp): FavoritesRepo

    @Singleton
    @Binds
    abstract fun provideProductRepository(productRepoImpl: ProductRepoImpl): ProductRepo

    @Singleton
    @Binds
    abstract fun bindWishListRepo(wishListRepoImp: WishListRepoImp): WishListRepo

    @Singleton
    @Binds
    abstract fun bindMediaRepo(mediaRepoImp: MediaRepoImp): MediaRepo

    @Singleton
    @Binds
    abstract fun bindStorageRepo(storageRepoImp: StorageRepoImp): StorageRepo

}