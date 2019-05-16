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
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.compose.format.FormatFragment
import kotlinx.android.synthetic.main.compose_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

private const val TOOL_AREA_ANIM_DURATION = 360L
private val TOOL_AREA_INTERPOLATOR: Interpolator = DecelerateInterpolator(2.5f)

private val TOOL_AREA_TRANSLATION_Y = 128f.dp

class ComposeFragment : BaseFragment<ComposeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.compose_fragment

    override val vm: ComposeViewModel by viewModel()

    private var isToolAreaEnable = false
    private var toolsBehavior: BottomSheetBehavior<FrameLayout> by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolsBehavior = BottomSheetBehavior.from(toolAreaContainer)
        toolsBehavior.peekHeight = TOOL_AREA_TRANSLATION_Y.toInt()

        TooltipCompat.setTooltipText(btnGallery, "图片")

        btnGallery.tooltipTextCompat = "从相册选择图片"
        btnGallery.click(vm) {
            toast("相册选择还没做")
        }

        btnFormat.tooltipTextCompat = "格式化工具"
        btnFormat.click(vm) {
            enableToolbarArea(!isToolAreaEnable)
            showFormatTools()
        }

        btnMention.tooltipTextCompat = "提及你的朋友"
        btnMention.click(vm) {
        }

        btnAssistant.tooltipTextCompat = "Potato Assistant (Alpha)"
        btnAssistant.click(vm) {
        }

        btnQuickText.tooltipTextCompat = "快捷输入"
        btnQuickText.click(vm) {
            enableToolbarArea(!isToolAreaEnable)
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

    private fun enableToolbarArea(enabled: Boolean) {
        if (enabled == isToolAreaEnable) {
            return
        }

        if (isToolAreaEnable) {
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
                    .withEndAction { toolAreaContainer.isVisible = false }
                    .start()
        } else {
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

        isToolAreaEnable = !isToolAreaEnable
    }
}
