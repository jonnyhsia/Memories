package com.arch.jonnyhsia.memories.model.trophy

import com.arch.jonnyhsia.foundation.model.DataSource
import com.arch.jonnyhsia.memories.model.trophy.bean.Trophy

interface TrophyDataSource : DataSource {

    fun haveEarnedTrophy(id: Int): Boolean

    fun earnTrophy(id: Int): Trophy?
}