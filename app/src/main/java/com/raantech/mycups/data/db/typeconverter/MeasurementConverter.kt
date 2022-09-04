package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import java.lang.reflect.Type

class MeasurementConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): Measurement? {
        val type: Type = object : TypeToken<Measurement?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun objectToStoredString(data: Measurement?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}