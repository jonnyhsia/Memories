package com.arch.jonnyhsia.memories.model.story.bean

class UpdateFriendsModel(
        val title: String,
        val friends: List<SimpleUserDisplayModel>
)

data class SimpleUserDisplayModel(
        val id: Int,
        val avatar: String,
        val nickname: String
)

