package com.raantech.mycups.utils

import com.raantech.mycups.data.models.general.Countries

object CountryToPhonePrefix {

    fun List<Countries>.getCountryByCode(code: String): Countries {
        return this.singleOrNull { it.code?.toLowerCase()?.contains(code.toLowerCase()) == true }
            ?: Countries(
                name = "Jordan",
                code = "+962"
            )
    }
}