package com.jonnyhsia.memories.page.main.account.storylist

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.compass.navigate
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.asVerticalList
import com.jonnyhsia.appcore.ui.XMultiAdapter
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.router.Params
import kotlinx.android.synthetic.main.story_list_fragment.*
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class StoryListFragment : BaseFragment<StoryListViewModel>(), StoryFlatBinderDelegate {
    override val layoutRes: Int
        get() = R.layout.story_list_fragment

    override val vm: StoryListViewModel by viewModel()

    private var storyListAdapter: XMultiAdapter by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyListAdapter = XMultiAdapter()
                .apply {
                    register(StoryFlatBinder(this@StoryListFragment))
                    register(LevelInfoBinder(View.OnClickListener {

                    }))
                }

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

//        recyclerStory.pagination<StoryDisplayModel> {
//            preloadOffset = 3
//            endView = view
//            mapper = { page ->
//                val dso = StoryRepository.getUserStories(0, page)
//                if (dso is Pageable<*>) {
//
//                }
//                TODO()
//                // 根据 page 请求接口
//            }
//        }
    }

    override fun onClickStory(story: StoryDisplayModel, position: Int) {
        navigate("memo://StoryDetail") {
            addParameter(Params.STORY_ID, story.id)
        }
    }
}


/*

Fragment:

pagination.getCurrentPage()
pagination = adapter.buildPagination(livedata) {
    preloadOffset = 5
    loadingView = null
    endView = null
    nextPageMapper { page ->
        ArticleRepository.getArticleList(page)
    }
}





 */