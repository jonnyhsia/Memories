package com.jonnyhsia.memories.page.main.discuss

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.memories.model.event.LoginEvent
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.arch.jonnyhsia.mirror.logger.logd
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.asVerticalList
import com.jonnyhsia.appcore.livebus.LiveBus
import com.jonnyhsia.appcore.ui.XMultiAdapter
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.discuss.binder.*
import kotlinx.android.synthetic.main.discuss_fragment.*
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscussFragment : BaseFragment<DiscussViewModel>() {

    override val layoutRes: Int
        get() = R.layout.discuss_fragment

    override val vm: DiscussViewModel by viewModel()

    private val adapter = XMultiAdapter()

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

        adapter.apply {
            register(TopDiscussListBinder())
            register(GroupListBinder())
            register(HeaderBinder())
            register(DiscussDisplayModel::class)
                    .to(DiscussWithoutImageBinder(), DiscussWithImageBinder())
                    .withLinker { _, t ->
                        if (t.image.isNullOrEmpty()) 0 else 1
                    }
        }

        recyclerDiscuss.asVerticalList()
        recyclerDiscuss.adapter = adapter

        vm.discussList.observe(this, Observer {
            adapter.setModels(it)
        })

        LiveBus.observe<LoginEvent>(this, Observer {
            logd("Discuss: 登录登出")
        })
    }
}