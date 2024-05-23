package com.example.recyclerview.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Fact (@StringRes val stringResourceId: Int,
                 @DrawableRes val imageResourceId: Int) {
}