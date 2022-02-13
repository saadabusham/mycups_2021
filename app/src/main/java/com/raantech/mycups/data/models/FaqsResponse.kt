package com.raantech.mycups.data.models

data class FaqsResponse(
        var id: Int,
        var question: String,
        var answer: String,
        var image: String,

        @Transient
        var isExpanded: Boolean = false
)