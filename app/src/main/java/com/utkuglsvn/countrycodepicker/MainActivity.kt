package com.utkuglsvn.countrycodepicker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.utkuglsvn.countrycodepicker.libData.utils.getLibCountries
import com.utkuglsvn.countrycodepicker.libUi.CountryCodePicker
import com.utkuglsvn.countrycodepicker.libUi.theme.ComposeCountryCodePickerTheme

class MainActivity : ComponentActivity() {
    
    private val countryCodePicker = CountryCodePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            ComposeCountryCodePickerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    countryCodePicker.CountryCodeDialog(
                        pickedCountry = {
                            Toast.makeText(
                                context,
                                "Selected Country ${it.countryName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
                        dialogSearch = true ,
                        dialogRounded = 22,
                        isCountryIconRounded = true
                    )
                }
            }
        }
    }
}