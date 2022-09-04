package com.raantech.mycups.data.db.typeconverter

import androidx.lifecycle.MutableLiveData
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import java.lang.reflect.Type

class MutableLiveDataIntegerConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): MutableLiveData<Int>? {
        val type: Type = object : TypeToken<MutableLiveData<Int>?>() {}.type
        return MutableLiveData(Gson().fromJson(value, type))
    }

    @TypeConverter
    fun objectToStoredString(data: MutableLiveData<Int>?): String? {
        val gson = Gson()
        return gson.toJson(data?.value?:0)
    }
}