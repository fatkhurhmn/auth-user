package com.muffar.authuser.utils

import android.util.Patterns
import com.muffar.authuser.R

object FormValidator {
    fun emailValidator(value: String): Int? {
        return when {
            value.isEmpty() -> R.string.email_is_required
            Patterns.EMAIL_ADDRESS.matcher(value).matches().not() -> R.string.email_is_invalid
            else -> null
        }
    }

    fun passwordValidator(value: String): Int? {
        return when {
            value.isEmpty() -> R.string.password_is_required
            value.length < 6 -> R.string.password_min_length
            else -> null
        }
    }

    fun confirmPasswordValidator(value: String, password: String): Int? {
        return when {
            value.isEmpty() -> R.string.confirm_password_is_required
            value != password -> R.string.passwords_do_not_match
            else -> null
        }
    }
}