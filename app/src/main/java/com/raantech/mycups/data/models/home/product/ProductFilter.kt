package com.raantech.mycups.data.models.home.product

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.enums.SortEnum
import java.io.Serializable

data class ProductFilter(

    @field:SerializedName("genderIds")
    val genderIds: String? = null,

    @field:SerializedName("dressCodeIds")
    val dressCodeIds: String? = null,

    @field:SerializedName("pageNumber")
    var pageNumber: Int = 1,

    @field:SerializedName("tagIds")
    val tagIds: String? = null,

    @field:SerializedName("sizeIds")
    val sizeIds: String? = null,

    @field:SerializedName("pageSize")
    val pageSize: Int = Constants.PAGE_SIZE,

    @field:SerializedName("brandIds")
    val brandIds: String? = null,

    @field:SerializedName("conditionIds")
    val conditionIds: String? = null,

    @field:SerializedName("colorIds")
    val colorIds: String? = null,

    @field:SerializedName("categoryIds")
    val categoryIds: String? = null,

    @field:SerializedName("styleIds")
    val styleIds: String? = null,

    @field:SerializedName("discountOnly")
    val discountOnly: Boolean? = null,

    @field:SerializedName("priceFrom")
    val priceFrom: Int? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("countryIds")
    val countryIds: String? = null,

    @field:SerializedName("getBookmarked")
    val getBookmarked: Boolean? = null,

    @field:SerializedName("trending_now")
    val trending_now: Boolean? = null,

    @field:SerializedName("related_product_id")
    val related_product_id: Int? = null,

    @field:SerializedName("sortType")
    var sortType: Int? = SortEnum.ALL.value,

    @field:SerializedName("seasonId")
    val seasonId: String? = null,

    @field:SerializedName("shopId")
    val shopId: Int? = null,

    @field:SerializedName("priceTo")
    val priceTo: Int? = null

) : Serializable