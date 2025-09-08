package com.example.cinema.util

import android.util.Patterns

object StringUtil {
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isEmpty(input: String?): Boolean {
        return input == null || input.isEmpty() || "" == input.trim { it <= ' ' }
    }

    fun getDoubleNumber(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else "" + number
    }
}