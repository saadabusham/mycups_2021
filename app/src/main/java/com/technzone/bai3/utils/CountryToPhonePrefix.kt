package com.technzone.bai3.utils

import com.technzone.bai3.data.models.general.Countries

object CountryToPhonePrefix {

    fun List<Countries>.getCountryByCode(code: String): Countries {
        return this.singleOrNull { it.code?.toLowerCase()?.contains(code.toLowerCase()) == true }
            ?: Countries(
                name = "Jordan",
                code = "+962"
            )
    }
}