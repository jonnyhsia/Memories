package com.arch.jonnyhsia.memories.model.story.bean


data class StoryDisplayModel(
        val id: Int,
        val title: String,
        val summary: String,
        val tags: List<String>,
        val dateText: String,
        val author: AuthorModel = AuthorModel.NULL,
        val image: String?,
        val imageRatio: Float = -1f
)

data class AuthorModel(
        val id: Int,
        val nickname: String,
        val avatar: String
) {
    companion object {
        val NULL = AuthorModel(0, "", "")
    }
}

data class FeaturedStoriesModel(
        val title: String,
        val list: List<StoryDisplayModel>
)

data class DiscussListModel(
        val title: String,
        val list: List<DiscussDisplayModel>
)

data class DiscussDisplayModel(
        val id: Int,
        val title: String,
        val description: String,
        val image:String,
        val meta: String
)