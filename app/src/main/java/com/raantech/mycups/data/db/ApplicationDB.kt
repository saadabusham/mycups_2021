package com.raantech.mycups.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raantech.mycups.data.daos.local.CartLocalDao
import com.raantech.mycups.data.db.typeconverter.*
import com.raantech.mycups.data.models.home.product.productdetails.Product

@Database(
    entities = [
        Product::class
    ], version = 1
)

@TypeConverters(
    CategoryConverter::class,
    MediaConverter::class,
    MediaListConverter::class,
    PriceConverter::class,
    MeasurementConverter::class,
    DesignCategoryConverter::class,
    MutableLiveDataBooleanConverter::class,
    MutableLiveDataIntegerConverter::class,
    ProductConverter::class,
    MeasurementListConverter::class,
    DesignCategoryListConverter::class
)

abstract class ApplicationDB : RoomDatabase() {

    abstract fun cartLocalDao(): CartLocalDao

    companion object {
        const val DATABASE_NAME = "mycubs.db"
        const val TABLE_CART = "cartTable"
    }

}