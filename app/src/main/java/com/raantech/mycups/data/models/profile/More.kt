package com.raantech.mycups.data.models.profile

import com.raantech.mycups.data.enums.MoreEnums

data class More(
    val title: String,
    val icon: Int?,
    val moreEnums: MoreEnums,
    val notificationCount: String? = "0"
)