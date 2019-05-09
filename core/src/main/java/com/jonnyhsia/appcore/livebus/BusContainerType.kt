package com.jonnyhsia.appcore.livebus

sealed class BusContainerType {

    class ArrayMap : BusContainerType()

    class HashMap : BusContainerType()
}