package com.technzone.bai3.data.pref.cart

interface CartPref {
    fun setCartList(list : List<Int>)
    fun addCart(id : Int)
    fun removeCart(id : Int)
    fun getCartList(): List<Int>
}