package com.technzone.bai3.common

import androidx.annotation.StringRes
import com.technzone.bai3.R

interface CommonEnums {

    enum class Languages(val value: String, @StringRes val languageName: Int) {
        English("en", R.string.english),
        Arabic("ar", R.string.arabic);

        companion object {
            fun getLanguageByValue(value: String): Languages {
                return when (value) {
                    "en" -> English
                    else -> Arabic
                }
            }
        }
    }


}
