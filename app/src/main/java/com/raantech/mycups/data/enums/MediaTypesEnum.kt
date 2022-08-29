package com.raantech.mycups.data.enums

enum class MediaTypesEnum(val value: String) {
    JPEG("jpeg"),
    PNG("png"),
    PSD("psd"),
    PDF("pdf"),
    IL("il"),
    DESIGN("psd,pdf,il"),
    IMAGES("jpeg,png"),
    ALL("jpeg,png,psd,pdf"),
}