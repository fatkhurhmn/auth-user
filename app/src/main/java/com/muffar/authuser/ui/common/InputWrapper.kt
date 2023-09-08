package com.muffar.authuser.ui.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputWrapper(
    val value: String = "",
    val errorMessage: Int? = null,
) : Parcelable