package com.jonnyhsia.memories.page.storydetail

import android.os.Bundle
import android.view.View
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.memories.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryDetailFragment : BaseFragment<StoryDetailViewModel>() {

    override val layoutRes: Int
        get() = R.layout.story_detail_fragment

    override val vm: StoryDetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}