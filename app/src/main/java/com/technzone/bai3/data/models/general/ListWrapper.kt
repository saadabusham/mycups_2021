package com.technzone.bai3.data.models.general

data class ListWrapper<M>(
    val data: ArrayList<M>?,
    val totalRows: Int?
)