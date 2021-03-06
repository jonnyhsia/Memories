package com.arch.jonnyhsia.memories.model.group

import com.arch.jonnyhsia.memories.model.DataSource
import io.reactivex.Single

interface GroupDataSource : DataSource {

    fun getList(): Single<List<Any>>
}