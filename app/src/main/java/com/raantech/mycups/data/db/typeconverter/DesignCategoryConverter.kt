package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import java.lang.reflect.Type

class DesignCategoryConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): DesignCategory? {
        val type: Type = object : TypeToken<DesignCategory?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun objectToStoredString(data: DesignCategory?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}