package com.arch.jonnyhsia.memories.model.story

import com.arch.jonnyhsia.memories.model.DataSource
import com.arch.jonnyhsia.memories.model.story.bean.EditableStory
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import io.reactivex.Single

interface StoryDataSource : DataSource {

    fun getTimeline(page: Int): Single<List<Any>>

    fun getUserStories(userId: Int, page: Int): Single<List<StoryDisplayModel>>

    fun save(title: String, content: String)

    fun getLatestDraft(): EditableStory?
}