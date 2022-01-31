package com.technzone.bai3.data.di


import com.technzone.bai3.data.repos.address.AddressRepo
import com.technzone.bai3.data.repos.address.AddressRepoImp
import com.technzone.bai3.data.repos.cart.CartRepo
import com.technzone.bai3.data.repos.cart.CartRepoImp
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.common.CommonRepoImp
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.data.repos.user.UserRepoImp
import com.technzone.bai3.data.repos.configuration.ConfigurationRepo
import com.technzone.bai3.data.repos.configuration.ConfigurationRepoImp
import com.technzone.bai3.data.repos.favorites.FavoritesRepo
import com.technzone.bai3.data.repos.favorites.FavoritesRepoImp
import com.technzone.bai3.data.repos.lookups.LookupsRepo
import com.technzone.bai3.data.repos.lookups.LookupsRepoImp
import com.technzone.bai3.data.repos.orders.OrdersRepo
import com.technzone.bai3.data.repos.orders.OrdersRepoImp
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
    abstract fun bindUserRepo(userRepoImp: UserRepoImp) : UserRepo

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

}