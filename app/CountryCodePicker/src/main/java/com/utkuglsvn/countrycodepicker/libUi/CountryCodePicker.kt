package com.utkuglsvn.countrycodepicker.libUi


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import androidx.compose.runtime.CompositionLocalProvider
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
        textColor: Color = Color.Black,
        backgroundColor: Color = Color.White,
        isOnlyFlagShow: Boolean = false,
        isShowIcon: Boolean = true,
        defaultSelectedCountry: CountryCode = getLibCountries().first(),
        pickedCountry: (CountryCode) -> Unit,
        dialogSearch: Boolean = true,
        dialogRounded: Int = 12,
        dialogTextColor: Color = Color.Black,
        dialogSearchHintColor: Color = Color.Gray,
        dialogTextSelectColor: Color = Color.Unspecified,
        dialogBackgroundColor: Color = Color.White,
    ) {
        val countryList: List<CountryCode> = getLibCountries()
        var isPickCountry by remember { mutableStateOf(defaultSelectedCountry) }
        var isOpenDialog by remember { mutableStateOf(false) }
        var searchValue by remember { mutableStateOf("") }

        Card(
            modifier = modifier
                .padding(3.dp)
                .clickable { isOpenDialog = true },
            backgroundColor = backgroundColor,
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
                            Modifier.padding(horizontal = 18.dp),
                            color = textColor,
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
                        backgroundColor = dialogBackgroundColor,
                        shape = RoundedCornerShape(dialogRounded.dp)
                    ) {
                        Column {
                            if (dialogSearch) {
                                searchValue = DialogSearchView(
                                    textColor = dialogTextColor,
                                    hintColor = dialogSearchHintColor,
                                    textSelectColor = dialogTextSelectColor,
                                )
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
                                            Modifier.padding(horizontal = 18.dp),
                                            color = dialogTextColor,
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
    private fun DialogSearchView(
        textColor: Color,
        hintColor: Color,
        textSelectColor: Color,
    ): String {
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
                textColor = textColor,
                hintColor = hintColor,
                textSelectColor = textSelectColor,
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
        textColor: Color,
        hintColor: Color,
        textSelectColor: Color,
        textAlign: TextAlign = TextAlign.Center
    ) {
        Box(modifier) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = textSelectColor,
                    backgroundColor = textSelectColor.copy(0.2f),
                )
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = textAlign,
                        fontSize = fontSize,
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = textColor.copy(0.2f)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = textColor,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textSelectColor,
                    )
                )
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body1,
                        color = hintColor,
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
}
