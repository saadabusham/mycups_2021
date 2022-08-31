package com.raantech.mycups.data.enums

enum class OrderStatusEnum(val value: Int) {
    WAITING_FOR_OFFER_PRICE(1),
    OFFER_PRICE_RECEIVED(2),
    SENT(3),
}