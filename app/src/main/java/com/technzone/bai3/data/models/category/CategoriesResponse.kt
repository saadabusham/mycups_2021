package com.technzone.bai3.data.models.category

import com.technzone.bai3.data.models.general.BaseHomeResponse

data class CategoriesResponse(
    val data: List<Category>?
) : BaseHomeResponse()
