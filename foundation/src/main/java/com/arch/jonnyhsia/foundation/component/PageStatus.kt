package com.arch.jonnyhsia.foundation.component

sealed class PageStatus {

    // object Uninitialized: PageStatus()

    object Loading : PageStatus()

    object IDLE : PageStatus()

    class Failed(val message: String) : PageStatus()

}