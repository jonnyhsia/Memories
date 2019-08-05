package com.jonnyhsia.memories.page.main.timeline

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.memories.model.event.LoginEvent
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.arch.jonnyhsia.mirror.logger.logd
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.asVerticalList
import com.jonnyhsia.appcore.ext.firstVisibleItemPosition
import com.jonnyhsia.appcore.ext.isExpanded
import com.jonnyhsia.appcore.livebus.LiveBus
import com.jonnyhsia.appcore.ui.XMultiAdapter
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.TabDoubleTap
import com.jonnyhsia.memories.page.main.discuss.binder.TopDiscussListBinder
import com.jonnyhsia.memories.page.main.timeline.binder.*
import kotlinx.android.synthetic.main.timeline_fragment.*
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class TimelineFragment : BaseFragment<TimelineViewModel>(), TabDoubleTap {

    override val vm: TimelineViewModel by viewModel()

    private var adapter: XMultiAdapter by Delegates.notNull()

    override val layoutRes: Int
        get() = R.layout.timeline_fragment

    private val placeholderAnim: Animator by lazy {
        ObjectAnimator.ofFloat(imgPlaceholder, "alpha", 0f, 1f)
                .apply {
                    duration = 1200L
                    repeatMode = ValueAnimator.REVERSE
                    repeatCount = ValueAnimator.INFINITE
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enableLoadingView(Observer {
            if (it == null) {
                placeholderAnim.cancel()
                imgPlaceholder.isVisible = false
            } else {
                imgPlaceholder.isVisible = true
                placeholderAnim.start()
            }
        })

        // collapsingToolbar.setPadding(0, statusBarHeight, 0, 0)
        adapter = XMultiAdapter().apply {
            register(FeaturedBinder())
            register(TopDiscussListBinder())
            register(StoryHeaderBinder())
            register(UpdateFriendsBinder())
            register(StartFromDraftBinder())
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

        LiveBus.observe<LoginEvent>(this, Observer {
            logd("Timeline: 登录登出了")
        })
    }

    override fun onTabDoubleTapped() {
        val layoutManager = recyclerTimeline.layoutManager ?: return
        if (layoutManager.firstVisibleItemPosition > 3) {
            recyclerTimeline.scrollToPosition(3)
        }
        recyclerTimeline.smoothScrollToPosition(0)

        if (!appBar.isExpanded(offset = 0)) {
            appBar.setExpanded(true)
        }
    }
}