package com.jonnyhsia.memories.page.main.account

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.arch.jonnyhsia.mirror.logger.logd
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.*
import com.jonnyhsia.appcore.ui.Drawables
import com.jonnyhsia.appcore.ui.rectangle
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.main.account.storylist.StoryListFragment
import kotlinx.android.synthetic.main.account_fragment.*
import kotlinx.android.synthetic.main.item_group_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.max
import kotlin.properties.Delegates

class AccountFragment : BaseFragment<AccountViewModel>() {

    override val layoutRes: Int
        get() = R.layout.account_fragment

    override val vm: AccountViewModel by viewModel()

    private var pagerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by Delegates.notNull()

    /** Toolbar 竖直伸缩的最大距离 */
    private var collapsingHeight: Int = 0
        get() {
            if (field == 0) {
                val value = appBar.measuredHeight - accountTabLayout.measuredHeight - toolbar.measuredHeight
                field = value
            }
            return field
        }

    /** 头像平移的最大距离 */
    private var avatarTranslateWidth: Float = 0f
        get() {
            if (field == 0f) {
                val value = displayWidth / 2 - 0.4f * 46.dp - 20.dp
                field = -value
            }
            return field
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgAvatar.load(R.drawable.placeholder_oval) {
            asAvatar()
        }
        tvEmail.setOnClickListener {

        }

        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            val percent = 1f * abs(offset) / collapsingHeight
            logd("offset: $offset, collpsing: $collapsingHeight")
            val scale = 1 - 0.6f * percent
            imgAvatar.scaleX = scale
            imgAvatar.scaleY = scale
            imgAvatar.translationY = 134.dp * percent
//            imgAvatar.translationX = avatarTranslateWidth * percent

            val alpha = max(0f, 1 - 2f * percent)
            tvUsername.alpha = alpha
            tvEmail.alpha = alpha
        })

        setupPagerAndTabs()
    }

    private fun setupPagerAndTabs() {
        pagerAccount.adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int = 2

            override fun getItem(position: Int): Fragment {
                return createFragmentAt(position)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> "07\n原创".spannable {
                        setSize(textSize = 18, end = 2)
                        setSize(textSize = 12, start = length - 2)
                    }
                    1 -> "130\n收藏".spannable {
                        setSize(textSize = 18, end = 3)
                        setSize(textSize = 12, start = length - 2)
                    }
                    else -> null
                }
            }
        }

        accountTabLayout.setupWithViewPager(pagerAccount)
    }

    private fun createFragmentAt(position: Int): Fragment {
        return when (position) {
            0 -> StoryListFragment()
            1 -> Fragment()
            else -> Fragment()
        }
    }
}