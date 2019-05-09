package com.jonnyhsia.memories.page.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.Colors
import com.jonnyhsia.appcore.ext.setForeground
import com.jonnyhsia.appcore.ext.spannable
import com.jonnyhsia.memories.R
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