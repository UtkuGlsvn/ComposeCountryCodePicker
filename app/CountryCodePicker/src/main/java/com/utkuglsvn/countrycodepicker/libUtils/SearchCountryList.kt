package com.utkuglsvn.countrycodepicker.libUtils

import com.utkuglsvn.countrycodepicker.libData.CountryCode

fun List<CountryCode>.searchCountryList(key: String): MutableList<CountryCode> {
    val tempList = mutableListOf<CountryCode>()
    this.forEach {
        if (it.countryName.lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}