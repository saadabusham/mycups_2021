package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import com.raantech.mycups.data.models.media.Media
import java.lang.reflect.Type

class MeasurementListConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): List<Measurement>? {
        val listType: Type = object : TypeToken<List<Measurement>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToStoredString(list: List<Measurement>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}