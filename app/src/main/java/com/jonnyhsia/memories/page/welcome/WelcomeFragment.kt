package com.jonnyhsia.memories.page.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.foundation.component.BaseFragment
import com.arch.jonnyhsia.foundation.ext.Colors
import com.arch.jonnyhsia.foundation.ext.setForeground
import com.arch.jonnyhsia.foundation.ext.spannable
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.ui.showTrophyToast
import kotlinx.android.synthetic.main.welcome_fragment.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.welcome_fragment

    override val vm: WelcomeViewModel by viewModel()

    private val bannerAdapter = MultiTypeAdapter()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.trophySignal.observe(this, Observer {
            requireContext().showTrophyToast(it)
        })

        bannerAdapter.register(WelcomeBannerBinder())
        bannerAdapter.items = vm.bannerList
        bannerAdapter.notifyDataSetChanged()

        btnLogin.setOnClickListener {
            vm.gotoLogin(entry = pageName())
        }

        tvTrick.setOnClickListener {
            vm.onClickTrick()
        }

        tvAgree.text = getString(R.string.u_should_agree)
                .spannable {
                    setForeground(color = Colors(R.color.colorAccent), start = length - 9, end = length - 5)
                    setForeground(color = Colors(R.color.colorAccent), start = length - 4, end = length)
                }
        tvAgree.setOnClickListener {
            vm.onClickAgree()
        }

        pagerWelcome.adapter = bannerAdapter
    }

}