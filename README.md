# ComposeCountryCodePicker
Built the ccp(https://github.com/hbb20/CountryCodePickerProject) code on compose. Country Code Picker is an android library which provides an easy way to search and select country or international phone code.

How to add in your project
-------------------------------------------------------------------------------
In the build.gradle add maven central repository

    repositories {
    maven { url 'https://jitpack.io' }
    }
Then, add library at app/build.gradle with following code

    implementation 'com.github.UtkuGlsvn:ComposeCountryCodePicker:1.0.4'
    
    
How to use project
-------------------------------------------------------------------------------

### Parameters taken by the function
 * modifier -> button card modifier
 * isOnlyFlagShow -> it only shows flag (remove country code) (default false, show country code)
 * defaultSelectedCountry -> which country code is selected (default Andorra)
 * pickedCountry -> The country you selected from the dialog
 * dialogSearch -> search on/off (default true)
 * dialogRounded -> set dialog roundend (default 12)



### Example


    private val countryCodePicker = CountryCodePicker() //create new object


    countryCodePicker.CountryCodeDialog(
                        pickedCountry = {
                            Toast.makeText(
                                context,
                                "Selected Country ${it.countryName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
                        dialogSearch = true,
                        dialogRounded = 22
                    )
                    
                    
                    
## Gif

<img src="https://github.com/UtkuGlsvn/ComposeCountryCodePicker/blob/master/gif/country_code_picker.gif" alt="drawing" width="250"/>


[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)




