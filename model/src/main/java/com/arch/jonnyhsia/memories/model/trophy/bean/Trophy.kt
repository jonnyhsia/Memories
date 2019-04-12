package com.arch.jonnyhsia.memories.model.trophy.bean

private const val USUAL = 1
private const val RARE = 2
private const val ULTRA_RARE = 3

sealed class Trophy(
        val id: Int,
        val name: String,
        val description: String,
        val difficulty: Int = USUAL
) {
    companion object {
        fun of(id: Int): Trophy? {
            return when (id) {
                1 -> FoundADifferentWay()
                2 -> HoldOn()
                else -> null
            }
        }
    }
}

class FoundADifferentWay :
        Trophy(id = 1, name = "另辟蹊径", description = "发现传说中能直接进入应用首页的隐藏入口，但切记一定要及时补票，否则……", difficulty = RARE)

class HoldOn :
        Trophy(id = 2, name = "坚持不懈", description = "你还蛮持久的嘛~ (各种含义", difficulty = USUAL)

//class Courage :
//        Trophy(id = 2, name = "不可为而为之", description = "", difficulty = RARE)