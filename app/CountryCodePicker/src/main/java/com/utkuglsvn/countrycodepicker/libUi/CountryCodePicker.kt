package com.utkuglsvn.countrycodepicker.libUi


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.utkuglsvn.countrycodepicker.libData.CountryCode
import com.utkuglsvn.countrycodepicker.libData.utils.getFlagMasterResID
import com.utkuglsvn.countrycodepicker.libData.utils.getLibCountries
import com.utkuglsvn.countrycodepicker.libUtils.searchCountryList

class CountryCodePicker {

    @Preview
    @Composable
    private fun PreviewCountryCodeDialog() {
        CountryCodeDialog(
            pickedCountry = {},
            defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
        )
    }
    @Preview
    @Composable
    private fun PreviewCountryCodeDialogNoIconReducedPadding() {
        CountryCodeDialog(
            pickedCountry = {},
            defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
            isOnlyFlagShow = true,
            isShowIcon = false,
            padding = 2.dp
        )
    }

    @Composable
    fun CountryCodeDialog(
        modifier: Modifier = Modifier,
        padding: Dp = 15.dp,
        isOnlyFlagShow: Boolean = false,
        isShowIcon: Boolean = true,
        defaultSelectedCountry: CountryCode = getLibCountries().first(),
        pickedCountry: (CountryCode) -> Unit,
        dialogSearch: Boolean = true,
        dialogRounded: Int = 12
    ) {
        val countryList: List<CountryCode> = getLibCountries()
        var isPickCountry by remember { mutableStateOf(defaultSelectedCountry) }
        var isOpenDialog by remember { mutableStateOf(false) }
        var searchValue by remember { mutableStateOf("") }

        Card(
            modifier = modifier
                .padding(3.dp)
                .clickable { isOpenDialog = true }
        ) {
            Column(modifier = Modifier.padding(padding)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = getFlagMasterResID(
                                isPickCountry.countryCode
                            )
                        ), contentDescription = null
                    )
                    if (!isOnlyFlagShow) {
                        Text(
                            "${isPickCountry.countryPhoneCode} ${isPickCountry.countryCode}",
                            Modifier.padding(horizontal = 18.dp)
                        )
                    }
                    if (isShowIcon) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            }

            //Dialog
            if (isOpenDialog) {
                Dialog(
                    onDismissRequest = { isOpenDialog = false },
                ) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.85f),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(dialogRounded.dp)
                    ) {
                        Column {
                            if (dialogSearch) {
                                searchValue = DialogSearchView()
                            }
                            LazyColumn {
                                items(
                                    (if (searchValue.isEmpty()) {
                                        countryList
                                    } else {
                                        countryList.searchCountryList(searchValue)
                                    })
                                ) { countryItem ->
                                    Row(
                                        Modifier
                                            .padding(
                                                horizontal = 18.dp,
                                                vertical = 18.dp
                                            )
                                            .clickable {
                                                pickedCountry(countryItem)
                                                isPickCountry = countryItem
                                                isOpenDialog = false
                                            }) {
                                        Image(
                                            painter = painterResource(
                                                id = getFlagMasterResID(
                                                    countryItem.countryCode
                                                )
                                            ), contentDescription = null
                                        )
                                        Text(
                                            countryItem.countryName,
                                            Modifier.padding(horizontal = 18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DialogSearchView(): String {
        var searchValue by remember { mutableStateOf("") }
        Row {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                value = searchValue,
                onValueChange = {
                    searchValue = it
                },
                fontSize = 14.sp,
                hint = "Search ...",
                textAlign = TextAlign.Start,
            )
        }
        return searchValue
    }

    @Composable
    private fun CustomTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        hint: String = "",
        fontSize: TextUnit = 16.sp,
        textAlign: TextAlign = TextAlign.Center
    ) {
        Box(
            modifier = modifier
                .background(
                    color = Color.White.copy(alpha = 0.1f)
                )
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = textAlign,
                    fontSize = fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black.copy(0.2f)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.then(
                        Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 52.dp)
                    )
                )
            }
        }
    }
}