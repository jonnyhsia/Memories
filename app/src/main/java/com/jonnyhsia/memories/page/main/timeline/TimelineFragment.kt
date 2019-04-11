package com.jonnyhsia.memories.page.main.timeline

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.foundation.component.BaseFragment
import com.arch.jonnyhsia.foundation.ext.statusBarHeight
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.arch.jonnyhsia.ui.ext.asVerticalList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.timeline.binder.*
import kotlinx.android.synthetic.main.timeline_fragment.*
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class TimelineFragment : BaseFragment<TimelineViewModel>() {

    override val vm: TimelineViewModel by viewModel()

    private var adapter: XMultiAdapter by Delegates.notNull()

    override val layoutRes: Int
        get() = R.layout.timeline_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbar.setPadding(0, statusBarHeight, 0, 0)

        adapter = XMultiAdapter().apply {
            register(FeaturedBinder())
            register(DiscussListBinder())
            register(StoryHeaderBinder())
            register(UpdateFriendsBinder())
            register(StoryDisplayModel::class)
                    .to(StoryWithoutImageBinder(), StoryWithImageBinder())
                    .withLinker { _, story ->
                        if (story.image.isNullOrEmpty()) 0 else 1
                    }
        }

        recyclerTimeline.asVerticalList()
        recyclerTimeline.adapter = adapter

        vm.timeline.observe(this, Observer {
            adapter.setModels(it)
        })
    }
}