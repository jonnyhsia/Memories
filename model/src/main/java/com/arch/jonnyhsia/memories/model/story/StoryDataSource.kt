package com.arch.jonnyhsia.memories.model.story

import com.arch.jonnyhsia.memories.model.DataSource
import io.reactivex.Single

interface StoryDataSource : DataSource {

    fun getTimeline(page: Int): Single<List<Any>>
}