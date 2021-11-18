package com.utkuglsvn.countrycodepicker.libData

import java.util.*

data class CountryCode(
    private var cCode: String,
    val countryPhoneCode: String = "",
    private val cName: String = "",
    val flagResID: Int = 0
) {
    val countryCode = cCode.lowercase(Locale.getDefault())
    val countryName = cName.lowercase(Locale.getDefault())
}