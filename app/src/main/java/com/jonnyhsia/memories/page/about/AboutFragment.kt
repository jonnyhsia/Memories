package com.jonnyhsia.memories.page.about

import android.os.Bundle
import android.view.View
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.memories.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment: BaseFragment<AboutViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_about

    override val vm: AboutViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}