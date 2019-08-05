package com.jonnyhsia.memories.page.compose

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannedString
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.memories.model.story.bean.StoryDraft
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jakewharton.rxbinding3.widget.textChanges
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.component.xsubscribe
import com.jonnyhsia.appcore.ext.*
import com.jonnyhsia.appcore.livebus.LiveBus
import com.jonnyhsia.appcore.okrx.okSubscribe
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.compose.format.FormatFragment
import com.jonnyhsia.memories.page.compose.quick.QuickTextAdapter
import com.zhihu.matisse.Matisse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.compose_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

private val TOOL_AREA_INTERPOLATOR: Interpolator = DecelerateInterpolator(2f)
private const val REQUEST_CHOOSE_IMAGE = 100

class ComposeFragment : BaseFragment<ComposeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.compose_fragment

    override val vm: ComposeViewModel by viewModel()

    private var quickTextAdapter: QuickTextAdapter? = null

    private var behavior: BottomSheetBehavior<RecyclerView> by Delegates.notNull()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior = BottomSheetBehavior.from(recyclerQuickText)
                .apply {
                    peekHeight = 122.dp
                }

        recyclerQuickText.setOnClickListener { }

        btnGallery.tooltipTextCompat = "从相册选择图片"
        btnGallery.click(vm) {
            openGallery()
        }

        btnFormat.tooltipTextCompat = "格式化工具"
        btnFormat.setOnCheckedChangeListener { _, checked ->
            enableToolbarArea(checked)
            if (checked) {
                btnQuickText.isChecked = false
                showFormatTools()
            }
        }

        btnMention.tooltipTextCompat = "提及你的朋友"
        btnMention.click(vm) {
        }

        btnQuickText.tooltipTextCompat = "快捷输入"
        btnQuickText.setOnCheckedChangeListener { _, checked ->
            quickInputArea.isVisible = checked
            if (checked) {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                btnFormat.isChecked = false
            }

            showQuickTextList(checked)
        }

        btnAssistant.tooltipTextCompat = "Potato Assistant (Alpha)"
        btnAssistant.click(vm) {
        }

        with(vm.savedDraft) {
            this ?: return@with fieldTitle.requestFocus()
            fieldContent.requestFocus()
            fieldTitle.setText(title)
            fieldContent.setText(content)
            fieldContent.setSelection(content.length)
        }

        swInformation.setFactory {
            TextView(requireContext()).apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                textSize = 12f
                setTextColor(Colors(R.color.textColorCaption))
                letterSpacing = 0.03f
            }
        }

        LiveBus.observe<StoryDraft>(this, Observer {
            swInformation.setText("自动保存成功")
        })

        Observable.combineLatest(
                fieldTitle.textChanges().skipInitialValue().debounce(500, TimeUnit.MILLISECONDS),
                fieldContent.textChanges().skipInitialValue().debounce(500, TimeUnit.MILLISECONDS),
                BiFunction<CharSequence, CharSequence, Unit> { _, _ -> }
        )
                .buffer(500, TimeUnit.MILLISECONDS)
                .xsubscribe(vm, onNext = {
                    vm.saveContent(fieldTitle.text.toString(), fieldContent.text.toString())
                })
    }

    private fun showQuickTextList(checked: Boolean) {
        recyclerQuickText.isVisible = true
        recyclerQuickText.animate()
                .setInterpolator(TOOL_AREA_INTERPOLATOR)
                .setDuration(450L)
                .translationY(if (checked) 0f else 122f.dp)
                .apply {
                    if (!checked) {
                        withEndAction { recyclerQuickText.isVisible = false }
                    }
                }
                .start()

        if (checked && quickTextAdapter == null) {
            quickTextAdapter = QuickTextAdapter { item, position ->
                val focusedView = view?.findFocus() ?: fieldContent.also { it.requestFocus() }
                (focusedView as? TextView)?.append(item.text)
            }
            recyclerQuickText.asFlexbox()
            recyclerQuickText.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), com.jonnyhsia.memories.R.anim.layout_anim_slide_in_from_bottom)
            recyclerQuickText.adapter = quickTextAdapter

            vm.quickTexts.observeLatest(this, Observer {
                quickTextAdapter?.items = it
                quickTextAdapter?.notifyDataSetChanged()
            })
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSE_IMAGE
                && resultCode == Activity.RESULT_OK) {
            val imageUri = Matisse.obtainResult(data).firstOrNull() ?: return
            imageUri2Bitmap(imageUri)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .okSubscribe(vm, onSuccess = {
                        fieldContent.append("\n")
                        val image = SpannableString("[]($imageUri)")
                        image.setImage(it, flag = SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE)
                        fieldContent.append(image)
                        fieldContent.append("\n")
                    })
        }
    }

    private fun imageUri2Bitmap(uri: Uri): Single<Bitmap> {
        return Single.fromCallable {
            val options = BitmapFactory.Options()
            options.inSampleSize = 4
            requireContext().contentResolver.openInputStream(uri)
                    .use {
                        BitmapFactory.decodeStream(it, null, options)
                    }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.saveContent(fieldTitle.text.toString(), fieldContent.text.toString())
    }
}
