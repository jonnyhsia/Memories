package com.arch.jonnyhsia.memories.model.story.bean

data class StoryHeaderModel(
        val title: String,
        val tags: List<TagModel>
)

data class TagModel(
        val id: Int,
        val text: String,
        val icon: String
)