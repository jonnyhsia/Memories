package com.jonnyhsia.memories.page.main.discuss

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.foundation.component.BaseFragment
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.arch.jonnyhsia.ui.ext.asVerticalList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }
}