package com.jonnyhsia.memories.page.compose

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.isVisible
import androidx.fragment.app.transaction
import com.arch.jonnyhsia.ui.ext.tooltipTextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.ext.click
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.application
import com.jonnyhsia.memories.page.compose.format.FormatFragment
import kotlinx.android.synthetic.main.compose_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

private const val TOOL_AREA_ANIM_DURATION = 360L
private val TOOL_AREA_INTERPOLATOR: Interpolator = DecelerateInterpolator(2.5f)

private val TOOL_AREA_TRANSLATION_Y = application.resources.getDimensionPixelSize(R.dimen.tool_area_translation_y).toFloat()

class ComposeFragment : BaseFragment<ComposeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.compose_fragment

    override val vm: ComposeViewModel by viewModel()

    private var isToolAreaEnable = false
    private var toolAreaSelectedIndex = -1
    private var toolsBehavior: BottomSheetBehavior<FrameLayout> by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolsBehavior = BottomSheetBehavior.from(toolAreaContainer)
        toolsBehavior.peekHeight = TOOL_AREA_TRANSLATION_Y.toInt()

        TooltipCompat.setTooltipText(btnGallery, "图片")

        btnBack.click(vm) {
            back()
        }

        btnGallery.tooltipTextCompat = "从相册选择图片"
        btnGallery.click(vm) {
            toast("相册选择还没做")
        }

        btnFormat.tooltipTextCompat = "格式化工具"
        btnFormat.click(vm) {
            enableToolbarArea(1)
            showFormatTools()
        }

        btnMention.tooltipTextCompat = "提及你的朋友"
        btnMention.click(vm) {
        }

        btnQuickText.tooltipTextCompat = "快捷输入"
        btnQuickText.click(vm) {
            enableToolbarArea(3)
        }

        btnAssistant.tooltipTextCompat = "Potato Assistant (Alpha)"
        btnAssistant.click(vm) {
        }
    }

    private fun showFormatTools() {
        childFragmentManager.transaction {
            val fragment = childFragmentManager.findFragmentByTag("format")
                    ?: FormatFragment()
            if (fragment.isVisible) {
                return
            }
            replace(R.id.toolAreaContainer, fragment)
        }
    }

    private fun enableToolbarArea(index: Int) {
        if (toolAreaSelectedIndex == index && isToolAreaEnable) {
            isToolAreaEnable = false
            toolAreaSelectedIndex = index
            // 还原
            toolbar.animate()
                    .translationY(0f)
                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
                    .setDuration(TOOL_AREA_ANIM_DURATION)
                    .start()

            toolsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            toolAreaContainer.animate()
                    .translationY(TOOL_AREA_TRANSLATION_Y)
                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
                    .setDuration(TOOL_AREA_ANIM_DURATION)
                    .withEndAction { toolAreaContainer.visibility = View.INVISIBLE }
                    .start()
            return
        }

        if (toolAreaSelectedIndex != index || !isToolAreaEnable) {
            toolAreaSelectedIndex = index
            isToolAreaEnable = true
            // 展开
            toolbar.animate()
                    .translationY(-TOOL_AREA_TRANSLATION_Y)
                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
                    .setDuration(TOOL_AREA_ANIM_DURATION)
                    .start()

            toolAreaContainer.isVisible = true
            toolAreaContainer.animate()
                    .translationY(0f)
                    .setInterpolator(TOOL_AREA_INTERPOLATOR)
                    .setDuration(TOOL_AREA_ANIM_DURATION)
                    .start()
        }
    }
}
