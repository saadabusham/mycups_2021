package com.raantech.mycups.data.models.category

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.general.BaseHomeResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem

data class CategoriesResponse(
    @field:SerializedName("categories")
    val categories: List<CategoriesItem>?
) : BaseHomeResponse()
