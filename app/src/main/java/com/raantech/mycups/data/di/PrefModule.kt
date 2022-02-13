package com.raantech.mycups.data.di

import android.content.Context
import com.raantech.mycups.data.pref.cart.CartPref
import com.raantech.mycups.data.pref.cart.CartPrefImp
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.pref.configuration.ConfigurationPrefImp
import com.raantech.mycups.data.pref.favorite.FavoritePref
import com.raantech.mycups.data.pref.favorite.FavoritePrefImp
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.data.pref.user.UserPrefImp
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
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