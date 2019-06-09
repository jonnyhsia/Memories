package com.jonnyhsia.memories.page.main

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.compass.navigate
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jonnyhsia.appcore.ext.Colors
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.account.AccountFragment
import com.jonnyhsia.memories.page.main.discuss.DiscussFragment
import com.jonnyhsia.memories.page.main.library.LibraryFragment
import com.jonnyhsia.memories.page.main.timeline.TimelineFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(name = "Main")
class MainActivity : AppCompatActivity(), Corgi {

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
            newTab(text = "首页", iconRes = R.drawable.ic_nav_timeline, animAsset = "tab_home.json")
            newTab(text = "讨论", iconRes = R.drawable.ic_nav_discuss, animAsset = "tab_discuss.json")
            newTab(text = "库", iconRes = R.drawable.ic_nav_msg, animAsset = "tab_collect.json")
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

        fabPost.setOnClickListener {
            navigate("memo://Compose")
        }

//        tabMine.setOnClickListener {
//            tabHome.visibility = View.INVISIBLE
//            tabHomeAnim.isVisible = true
//            tabHomeAnim.playAnimation()
//        }
    }

    private fun changeHomeFragment(oldPos: Int, pos: Int) {
        if (pos == 0 || pos == 1) {
            val colorRes = if (pos == 0) R.color.colorAccent else android.R.color.holo_green_light
            val color = ColorStateList.valueOf(Colors(colorRes))
            if (fabPost.isShown) {
                fabPost.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                    override fun onHidden(fab: FloatingActionButton?) {
                        fabPost.supportBackgroundTintList = color
                        fabPost.show()
                    }
                })
            } else {
                fabPost.supportBackgroundTintList = color
                fabPost.show()
            }
        } else {
            fabPost.hide()
        }

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
            2 -> LibraryFragment()
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
