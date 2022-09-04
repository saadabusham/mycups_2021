package com.raantech.mycups.data.daos.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raantech.mycups.data.db.ApplicationDB.Companion.TABLE_CART
import com.raantech.mycups.data.models.home.product.productdetails.Product

@Dao
interface CartLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCart(accessory: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCarts(accessories: List<Product>)

    @Query("SELECT * FROM $TABLE_CART ORDER BY id ASC")
    suspend fun getCarts(): List<Product>

    @Query("SELECT * FROM $TABLE_CART WHERE id = :id")
    suspend fun getCarts(id: Int): Product

    @Query("SELECT SUM(qty) FROM $TABLE_CART")
    fun getCartsCount(): LiveData<Int>

    @Query("SELECT SUM(qty) FROM $TABLE_CART")
    suspend fun getCartsCountInt(): Int?

    @Query("DELETE FROM $TABLE_CART WHERE id = :id")
    suspend fun deleteCart(id: Int)

    @Query("DELETE FROM $TABLE_CART")
    suspend fun clearCart()
}