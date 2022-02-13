package com.raantech.mycups.data.models.category

import com.raantech.mycups.data.models.general.BaseHomeResponse

data class CategoriesResponse(
    val data: List<Category>?
) : BaseHomeResponse()
