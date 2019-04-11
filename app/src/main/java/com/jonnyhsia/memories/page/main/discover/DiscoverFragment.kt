package com.jonnyhsia.memories.page.main.discover

import com.arch.jonnyhsia.foundation.component.BaseFragment
import com.jonnyhsia.memories.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : BaseFragment<DiscoverViewModel>() {

    override val layoutRes: Int
        get() = R.layout.discover_fragment

    override val vm: DiscoverViewModel by viewModel()

}