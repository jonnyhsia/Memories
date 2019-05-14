package com.jonnyhsia.memories.page.main.account.storylist

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.compass.navigate
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.arch.jonnyhsia.ui.ext.asVerticalList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
import com.google.android.material.appbar.AppBarLayout
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.router.Params
import kotlinx.android.synthetic.main.account_fragment.*
import kotlinx.android.synthetic.main.story_list_fragment.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class StoryListFragment : BaseFragment<StoryListViewModel>(), StoryFlatBinderDelegate {
    override val layoutRes: Int
        get() = R.layout.story_list_fragment

    override val vm: StoryListViewModel by viewModel()

    private val storyListAdapter = XMultiAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enableLoadingView(Observer {
            progressBar.isVisible = it != null
        })

        vm.storyList.observe(this, Observer {
            storyListAdapter.setModels(it)
        })

        val decoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        decoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical_20dp)!!)
        recyclerStory.addItemDecoration(decoration)
        recyclerStory.asVerticalList()
        recyclerStory.adapter = storyListAdapter
                .apply {
                    register(StoryFlatBinder(this@StoryListFragment))
                    register(LevelInfoBinder(View.OnClickListener {

                    }))
                }
    }

    override fun onClickStory(story: StoryDisplayModel, position: Int) {
        navigate("memo://StoryDetail") {
            addParameter(Params.STORY_ID, story.id)
        }
    }
}