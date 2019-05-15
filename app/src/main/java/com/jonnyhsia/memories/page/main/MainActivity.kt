package com.jonnyhsia.memories.page.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.arch.jonnyhsia.compass.api.Route
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.account.AccountFragment
import com.jonnyhsia.memories.page.main.discuss.DiscussFragment
import com.jonnyhsia.memories.page.main.timeline.TimelineFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(name = "Main")
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageBeforeEnterHome = vm.onEnterHome()
        if (pageBeforeEnterHome != null) {
            pageBeforeEnterHome.go(this)
            finish()
            return
        }

        // 进入首页
        setContentView(R.layout.activity_main)

//        tabHomeAnim.setAnimation("tab_home.json")

        bottomNavigation.build {
            newTab(text = "时间线", iconRes = R.drawable.ic_nav_timeline, animAsset = "tab_home.json")
            newTab(text = "讨论", iconRes = R.drawable.ic_nav_discuss, animAsset = "tab_discuss.json")
            newTab(text = "收藏", iconRes = R.drawable.ic_nav_msg, animAsset = "tab_collect.json")
            newTab(text = "我的", iconRes = R.drawable.ic_nav_account, animAsset = "tab_mine.json")

            setOnTabSelectListener { oldPos, pos ->
                changeHomeFragment(oldPos, pos)
                return@setOnTabSelectListener false
            }

            setOnTabReselectListener {
                val currentFragment = supportFragmentManager.findFragmentByTag(getFragmentTagAt(index = it))
                (currentFragment as? TabDoubleTap)?.onTabDoubleTapped()
            }
        }

//        tabMine.setOnClickListener {
//            tabHome.visibility = View.INVISIBLE
//            tabHomeAnim.isVisible = true
//            tabHomeAnim.playAnimation()
//        }
    }

    private fun changeHomeFragment(oldPos: Int, pos: Int) {
        val lastFragment = supportFragmentManager.findFragmentByTag(getFragmentTagAt(index = oldPos))
        val currFragment = supportFragmentManager.findFragmentByTag(getFragmentTagAt(index = pos))
        supportFragmentManager.transaction {
            lastFragment?.let(::hide)
            if (currFragment == null) {
                add(R.id.fragmentContainer, createFragmentAt(index = pos), getFragmentTagAt(index = pos))
            } else {
                show(currFragment)
            }
        }
    }

    private fun createFragmentAt(index: Int): Fragment {
        return when (index) {
            0 -> TimelineFragment()
            1 -> DiscussFragment()
            2 -> Fragment()
            3 -> {
                // Repository.getMiscDataSource().letWelcomePageEntered(entered = false)
                // Repository.getPassportDataSource().logout()
                AccountFragment()
            }
            else -> throw RuntimeException("错误的索引")
        }
    }

    private fun getFragmentTagAt(index: Int): String {
        return "home_$index"
    }
}
