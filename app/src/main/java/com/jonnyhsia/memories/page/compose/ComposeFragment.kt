package com.jonnyhsia.memories.page.compose

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.transaction
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.ui.ImageToggle
import com.arch.jonnyhsia.ui.ext.tooltipTextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.component.xsubscribe
import com.jonnyhsia.appcore.ext.Colors
import com.jonnyhsia.appcore.ext.click
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.application
import com.jonnyhsia.memories.page.compose.format.FormatFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.compose_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

private const val TOOL_AREA_ANIM_DURATION = 360L
private val TOOL_AREA_INTERPOLATOR: Interpolator = DecelerateInterpolator(2.5f)

private val TOOL_AREA_TRANSLATION_Y = application.resources.getDimensionPixelSize(R.dimen.tool_area_height).toFloat()

class ComposeFragment : BaseFragment<ComposeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.compose_fragment

    override val vm: ComposeViewModel by viewModel()

    private var toolAreaSelectedIndex = -1

    private var behavior: BottomSheetBehavior<RecyclerView> by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior = BottomSheetBehavior.from(recyclerQuickText)
        behavior.peekHeight = 122.dp
        recyclerQuickText.setOnClickListener { }

        btnBack.click(vm) {
            back()
        }

        btnGallery.tooltipTextCompat = "从相册选择图片"
        btnGallery.click(vm) {
            toast("相册选择还没做")
        }

        btnFormat.tooltipTextCompat = "格式化工具"
        btnFormat.setOnCheckedChangeListener(object : ImageToggle.OnCheckedChangeListener {
            override fun onCheckedChanged(view: ImageToggle, checked: Boolean) {
                enableToolbarArea(checked)
                if (checked) {
                    btnQuickText.isChecked = false
                    showFormatTools()
                }
            }
        })

        btnMention.tooltipTextCompat = "提及你的朋友"
        btnMention.click(vm) {
        }

        btnQuickText.tooltipTextCompat = "快捷输入"
        btnQuickText.setOnCheckedChangeListener(object : ImageToggle.OnCheckedChangeListener {
            override fun onCheckedChanged(view: ImageToggle, checked: Boolean) {
                quickInputArea.isVisible = checked
                if (checked) {
                    btnFormat.isChecked = false
                }

                recyclerQuickText.isVisible = true
                recyclerQuickText.animate()
                        .translationY(if (checked) 0f else 122f.dp)
                        .apply {
                            if (!checked) {
                                withEndAction { recyclerQuickText.isVisible = false }
                            }
                        }
                        .start()
            }
        })
//        btnQuickText.click(vm) {
//            // enableToolbarArea(3)
//            quickInputArea.isVisible = true
//            recyclerQuickText.isVisible = true
//            recyclerQuickText.animate()
//                    .translationY(0f)
//                    .start()
//        }

        btnAssistant.tooltipTextCompat = "Potato Assistant (Alpha)"
        btnAssistant.click(vm) {
        }

        swInformation.setFactory {
            TextView(requireContext()).apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                textSize = 13f
                setTextColor(Colors(R.color.textColorCaption))
                letterSpacing = 0.03f
            }
        }

        Observable.intervalRange(0, 10, 2000L, 3000L, TimeUnit.MILLISECONDS)
                .map {
                    if (it % 2 == 0L) "已成功自动保存" else ""
                }
                .observeOn(AndroidSchedulers.mainThread())
                .xsubscribe(vm, onNext = {
                    swInformation.setText(it)
                })
    }

    private fun showFormatTools() {
        childFragmentManager.transaction {
            val fragment = childFragmentManager.findFragmentByTag("format")
                    ?: FormatFragment()
            if (fragment.isAdded) {
                return
            }
            replace(R.id.toolAreaContainer, fragment, "format")
        }
    }

    private fun enableToolbarArea(enable: Boolean) {
        if (!enable) {
            // 还原
            toolAreaContainer.isVisible = false
//            isToolAreaEnable = false
//            toolAreaSelectedIndex = index
//            toolbar.animate()
//                    .translationY(0f)
//                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
//                    .setDuration(TOOL_AREA_ANIM_DURATION)
//                    .start()
//
//            toolAreaContainer.animate()
//                    .translationY(TOOL_AREA_TRANSLATION_Y)
//                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
//                    .setDuration(TOOL_AREA_ANIM_DURATION)
//                    .withEndAction { toolAreaContainer.visibility = View.INVISIBLE }
//                    .start()
            return
        }

        if (enable) {
            // 展开
            toolAreaContainer.isVisible = true
//            isToolAreaEnable = true
//            toolbar.animate()
//                    .translationY(-TOOL_AREA_TRANSLATION_Y)
//                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
//                    .setDuration(TOOL_AREA_ANIM_DURATION)
//                    .start()
//
//            toolAreaContainer.isVisible = true
//            toolAreaContainer.animate()
//                    .translationY(0f)
//                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
//                    .setDuration(TOOL_AREA_ANIM_DURATION)
//                    .start()
        }
    }
}
