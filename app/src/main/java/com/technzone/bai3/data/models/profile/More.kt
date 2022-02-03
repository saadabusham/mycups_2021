package com.technzone.bai3.data.models.profile

import com.technzone.bai3.data.enums.MoreEnums

data class More(
    val title: String,
    val icon: Int?,
    val moreEnums: MoreEnums,
    val notificationCount: String? = "0"
)