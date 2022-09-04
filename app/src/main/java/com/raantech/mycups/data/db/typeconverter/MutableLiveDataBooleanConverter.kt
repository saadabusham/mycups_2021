package com.raantech.mycups.data.db.typeconverter

import androidx.lifecycle.MutableLiveData
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import java.lang.reflect.Type

class MutableLiveDataBooleanConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): MutableLiveData<Boolean>? {
        val type: Type = object : TypeToken<MutableLiveData<Boolean>?>() {}.type
        return MutableLiveData(Gson().fromJson(value, type))
    }

    @TypeConverter
    fun objectToStoredString(data: MutableLiveData<Boolean>?): String? {
        val gson = Gson()
        return gson.toJson(data?.value?:false)
    }
}