package com.raantech.mycups.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.media.Media
import java.lang.reflect.Type

class DesignCategoryListConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): List<DesignCategory>? {
        val listType: Type = object : TypeToken<List<DesignCategory>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToStoredString(list: List<DesignCategory>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}