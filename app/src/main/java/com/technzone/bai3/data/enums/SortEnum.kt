package com.technzone.bai3.data.enums


enum class SortEnum(val value: Int?) {
    ALL(null),
    BEST_MATCH(1),
    POPULAR_ITEM(2),
    PRICE_LOW_TO_HIGH(3),
    PRICE_HIGH_TO_LOW(4),
    TOP_RATED(5),
    NEWEST_ITEM(6)
}