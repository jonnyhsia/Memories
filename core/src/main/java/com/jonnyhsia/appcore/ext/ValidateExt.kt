package com.jonnyhsia.appcore.ext

fun CharSequence.isValidEmail(): Boolean {
    return "[\\w!#\$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#\$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"
            .toRegex().matches(this)
}