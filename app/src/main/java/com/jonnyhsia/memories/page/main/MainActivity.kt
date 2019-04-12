package com.jonnyhsia.memories.page.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.memories.model.Repository
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.discover.DiscoverFragment
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

        bottomNavigation.setOnTabSelectListener { oldPos, pos ->
            changeHomeFragment(oldPos, pos)
            return@setOnTabSelectListener false
        }
        bottomNavigation.setOnTabReselectListener {
            val currentFragment = supportFragmentManager.findFragmentByTag(getFragmentTagAt(index = it))
            (currentFragment as? TabDoubleTap)?.onTabDoubleTapped()
        }
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
            1 -> DiscoverFragment()
            2 -> Fragment()
            3 -> Fragment()
            4 -> {
                Repository.getMiscDataSource().letWelcomePageEntered(entered = false)
                Repository.getPassportDataSource().logout()
                Fragment()
            }
            else -> throw RuntimeException("错误的索引")
        }
    }

    private fun getFragmentTagAt(index: Int): String {
        return "home_$index"
    }
}
