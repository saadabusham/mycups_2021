package com.technzone.bai3.data.pref.cart

import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import javax.inject.Inject

class CartPrefImp @Inject constructor(private val prefUtil: SharedPreferencesUtil) :
    CartPref {

    override fun setCartList(list: List<Int>) {
        val gson = com.google.gson.Gson()
        val json = gson.toJson(list)
        prefUtil.setStringPreferences(Constants.BundleData.CART_LIST, json)
    }

    override fun addCart(id: Int) {
        val list = getCartList().toMutableList()
        list.singleOrNull { it == id}?:also {
            list.add(id)
        }
        setCartList(list)
    }

    override fun removeCart(id: Int) {
        val list = getCartList().toMutableList()
        list.withIndex().singleOrNull { it.value == id}?.let {
            list.removeAt(it.index)
        }
        setCartList(list)
    }

    override fun getCartList(): List<Int> {
        val gson = com.google.gson.Gson()
        val json: String = prefUtil.getStringPreferences(Constants.BundleData.CART_LIST, "")
        if (json.isNullOrEmpty())
            return mutableListOf()
        return gson.fromJson(json, Array<Int>::class.java).asList()
    }
}