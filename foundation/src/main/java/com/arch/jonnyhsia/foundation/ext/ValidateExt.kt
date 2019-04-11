package com.arch.jonnyhsia.foundation.ext

fun CharSequence.isValidEmail(): Boolean {
    return "[\\w!#\$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#\$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"
            .toRegex().matches(this)
}