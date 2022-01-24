package com.technzone.bai3.data.di

import android.content.Context
import com.technzone.bai3.data.pref.cart.CartPref
import com.technzone.bai3.data.pref.cart.CartPrefImp
import com.technzone.bai3.data.pref.configuration.ConfigurationPref
import com.technzone.bai3.data.pref.configuration.ConfigurationPrefImp
import com.technzone.bai3.data.pref.favorite.FavoritePref
import com.technzone.bai3.data.pref.favorite.FavoritePrefImp
import com.technzone.bai3.data.pref.user.UserPref
import com.technzone.bai3.data.pref.user.UserPrefImp
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PrefModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesUtil(@ApplicationContext context: Context): SharedPreferencesUtil {
        return SharedPreferencesUtil.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideConfigurationPref(preferencesUtil: SharedPreferencesUtil): ConfigurationPref {
        return ConfigurationPrefImp(preferencesUtil)
    }

    @Provides
    @Singleton
    fun provideUserPref(preferencesUtil: SharedPreferencesUtil): UserPref {
        return UserPrefImp(preferencesUtil)
    }

    @Singleton
    @Provides
    fun provideFavoritePreferences(preferencesUtil: SharedPreferencesUtil): FavoritePref =
        FavoritePrefImp(preferencesUtil)

    @Singleton
    @Provides
    fun provideCartPreferences(preferencesUtil: SharedPreferencesUtil): CartPref =
        CartPrefImp(preferencesUtil)

}