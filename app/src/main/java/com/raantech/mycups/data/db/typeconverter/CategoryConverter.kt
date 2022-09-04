package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import java.lang.reflect.Type

class CategoryConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): CategoriesItem? {
        val type: Type = object : TypeToken<CategoriesItem?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun objectToStoredString(data: CategoriesItem?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}