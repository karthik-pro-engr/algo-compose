package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

data class VswStrings(
    @StringRes val titleRes:Int,
    @StringRes val bodyRes:Int,
    @StringRes val capacityLabelRes: Int,
    @StringRes val capacityPlaceholderRes: Int,
    @StringRes val capacityButtonRes: Int,
    @StringRes val itemLabelRes: Int,
    @StringRes val itemPlaceholderRes: Int,
    @StringRes val itemButtonRes: Int,
    @StringRes val noItemsInfoRes: Int,
    @StringRes val computeButtonRes: Int,
    @PluralsRes val unitPluralRes: Int,
    @StringRes val capacityAddedTextRes: Int,
    @StringRes val resultTextRes: Int,
)
