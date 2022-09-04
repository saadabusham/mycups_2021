package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.home.product.productdetails.Product
import java.lang.reflect.Type

class ProductConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): Product? {
        val type: Type = object : TypeToken<Product?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun objectToStoredString(data: Product?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}