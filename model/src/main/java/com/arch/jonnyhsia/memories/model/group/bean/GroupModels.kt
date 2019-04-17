package com.arch.jonnyhsia.memories.model.group.bean

import com.arch.jonnyhsia.memories.model.story.bean.GroupDisplayModel

data class GroupTagModel(val id: Int, val name: String)

data class GroupListModel(
        val tags: List<GroupTagModel>,
        val groupOfTagList: List<List<GroupDisplayModel>>)