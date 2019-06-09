package com.jonnyhsia.memories.page.main.library

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.account.storylist.StoryListFragment
import kotlinx.android.synthetic.main.library_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseFragment<LibraryViewModel>() {
    override val layoutRes: Int
        get() = R.layout.library_fragment

    override val vm: LibraryViewModel by viewModel()

    private lateinit var pagerAdapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = LibraryPagerAdapter(childFragmentManager)
        libraryPager.adapter = pagerAdapter
        libraryTabLayout.setupWithViewPager(libraryPager)
    }

    @SuppressLint("WrongConstant")
    class LibraryPagerAdapter(fm: FragmentManager) :
            FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val titles = arrayOf("原创", "收藏", "稿件")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> StoryListFragment()
                1 -> StoryListFragment()
                else -> Fragment()
            }
        }

        override fun getCount() = 3

        override fun getPageTitle(position: Int) = titles[position]
    }
}