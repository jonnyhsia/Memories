package com.arch.jonnyhsia.memories.model.trophy

import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.memories.model.trophy.bean.Trophy

object TrophyRepository : Repository(), TrophyDataSource {

    override fun haveEarnedTrophy(id: Int): Boolean {
        return false
    }

    override fun earnTrophy(id: Int): Trophy? {
        val trophy = Trophy.of(id)
        if (haveEarnedTrophy(id)) {
            return null
        }
        return trophy
    }
}