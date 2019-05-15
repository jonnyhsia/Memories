package com.arch.jonnyhsia.ui.recyclerview

import android.os.Handler
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.arch.jonnyhsia.mirror.logger.logd
import com.arch.jonnyhsia.mirror.logger.loge
import com.arch.jonnyhsia.ui.ext.lastVisibleItemPosition
import me.drakeet.multitype.*


typealias OnPrefetchListener = () -> Unit

/**
 * Multi Type Adapter 的拓展
 *
 * 1. 状态
 * 如要实现 Empty, 注册 State.Empty 类型的 ViewBinder 即可
 * TODO: 同理 (Pre)Fetching, NoMore, Error 等状态, 注册相应类型的 ViewBinder
 *
 * 2. 预加载
 * [prefetchNumber] > 0 且 [onPrefetch] 非空时生效
 *
 * 3. 更多 feature
 */

@Suppress("UNCHECKED_CAST")
val MultiTypeAdapter.mutableItems: ArrayList<Any>
    get() = items as ArrayList<Any>


@Suppress("UNCHECKED_CAST")
fun <T> MultiTypeAdapter.changeItemAt(position: Int, mapper: T.() -> T) {
    val item = items[position] as T
    mutableItems[position] = item.mapper()!!
}

open class XMultiAdapter @JvmOverloads constructor(
        items: List<*> = emptyList<Any>(),
        pool: TypePool = MultiTypePool()
) : MultiTypeAdapter(items, pool), Corgi {

    constructor(items: List<*>, initialCapacity: Int) : this(items, MultiTypePool(initialCapacity))

    private var isDataReady = false

    /** 预加载回调 */
    var onPrefetch: OnPrefetchListener? = null

    /** 预加载提前数量 */
    var prefetchNumber = 3
        set(value) {
            field = value
            // 若 number < 1, 则关闭预加载
            if (value < 0 && prefetchEnabled) {
                prefetchEnabled = false
            }
        }

    var prefetchListener: OnPrefetchListener? = null

    /** 预加载是否开启 */
    var prefetchEnabled = true
        set(value) {
            field = value
            // 若开启预加载, 且 number < 1, 默认设置 3
            if (value && prefetchNumber < 1) {
                prefetchNumber = 3
            }
        }

    /** 预加载指示器 */
    // var prefetchIndicator: View? = null

    var noMoreEnabled = true

    private var state: State = State.IDLE

    /** 在数据设置后再显示 Empty View */
    var showEmptyViewAfterDataReady = true

    private var isObserverRegistered = false
    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkDataIsEmpty()
            checkMoreDataPosition()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkDataIsEmpty()
            checkMoreDataPosition()
        }
    }

    private fun checkMoreDataPosition() {

    }

    private fun checkDataIsEmpty() {
        // item count = 0, 且注册了 Empty
        if (itemCount == 0 && typePool.firstIndexOf(State.Empty::class) != -1) {
            setModels(listOf(State.Empty))
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (!isObserverRegistered) {
            isObserverRegistered = true
            registerAdapterDataObserver(adapterDataObserver)
        }
        if (!showEmptyViewAfterDataReady) {
            checkDataIsEmpty()
        }

        setupPrefetch(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        unregisterAdapterDataObserver(adapterDataObserver)
    }

    private fun setupPrefetch(recyclerView: RecyclerView) {
        // 判断是否开启预加载
        if (!prefetchEnabled || prefetchListener == null) {
            return
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager ?: return
                    checkShouldFetching(layoutManager.lastVisibleItemPosition)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager ?: return
                checkShouldFetching(layoutManager.lastVisibleItemPosition)
            }
        })
    }

    /**
     * 对应 position 是否到了预加载的界限
     */
    private fun Int.shouldPrefetch(): Boolean {
        logd("position:$this\n" +
                "this + prefetchNumber:${this + prefetchNumber}\n" +
                "itemCount:$itemCount")
        return this + 1 + prefetchNumber >= itemCount
    }

    private fun startFetching() {
        state = State.Fetching
        if (typePool.firstIndexOf(State.Fetching::class) > -1) {
            addModels(listOf(State.Fetching))
        }
        prefetchListener?.invoke()
    }

    /**
     * 加载完成
     */
    fun setFetchSuccess() {
        state = State.IDLE
        if (prefetchListener != null && prefetchNumber >= 0) {
            prefetchEnabled = true
        }
    }

    fun setFetchFailed() {
        state = State.IDLE
        // if (prefetchListener != null && prefetchNumber >= 0) {
        //     prefetchEnabled = true
        // }
    }

    /**
     * 加载完成, 不再获取新数据
     */
    fun setFetchEnded() {
        prefetchEnabled = false
        state = State.NoMore

        if (items.contains(State.Fetching)) {
            items = Items(items).also { it.remove(State.Fetching) }
            notifyItemRemoved(itemCount)
        }

        // 如果
        if (noMoreEnabled) {
            if (typePool.firstIndexOf(State.NoMore::class) > -1) {
                addModels(listOf(State.NoMore))
            }
        }
    }

    fun checkShouldFetching(position: Int) {
        logd("prefetchEnabled:$prefetchEnabled\n" +
                "state=${state::class.java.simpleName}\n" +
                "shouldPrefetch:${position.shouldPrefetch()}")
        if (prefetchEnabled &&
                state != State.Fetching &&
                !items.contains(State.Loading) &&
                position.shouldPrefetch() &&
                itemCount > 0) {
            startFetching()
        }
    }

    fun setError() {
        setModels(listOf(State.Error))
    }

    // TODO: 1.上拉加载更多

    // TODO: 2.EmptyView

    //    final override fun setItems(items: MutableList<*>) {
//        super.setItems(items)
//        // 设置 data 后, isDataSet 为 true
//        if (!isDataSet) {
//            isDataSet = true
//        }
//    }

    /**
     * 设置新的数据源
     */
    fun setModels(models: List<*>) {
        items = Items(models)
        notifyDataSetChanged()
        isDataReady = true
    }

    fun showLoading() {
        if (!isTypeRegistered<State.Loading>()) {
            loge(null, "没注册Loading的binder")
            // register(LoadingViewBinder())
            return
        }
        setModels(listOf(State.Loading))
    }

    /**
     * 更新对应位置的 Item
     *
     * @param models   需要传入完整的列表数据
     * @param position 更新的位置
     */
    fun updateModel(model: Any, position: Int) {
        items = Items(items).also {
            it[position] = model
        }

        notifyItemChanged(position)
    }

    fun removeModel(pos: Int) {
        items = Items(items).also {
            it.removeAt(pos)
        }
        notifyItemRemoved(pos)
    }

    fun moveModel(model: Any, from: Int, to: Int) {
        val oldModel = items[from]

        items = Items(items).also {
            it.removeAt(from)
            it.add(to, model)
        }
        notifyItemMoved(from, to)

        // 判断是否更新 model 对应的 view
        if (model != oldModel) {
            notifyItemChanged(to)
        }
    }

    fun setModelsByDiff(newModels: List<*>, callback: DiffUtil.Callback) {
        if (itemCount == 0) {
            setModels(newModels)
            return
        }
        val result = DiffUtil.calculateDiff(callback)
        items = Items(newModels)
        result.dispatchUpdatesTo(this)
    }

    fun setModelsByDiff(callback: CommonDiffCallback) {
        setModelsByDiff(callback.getNewList()!!, callback)
    }

    fun isTypeRegistered(clz: Class<*>): Boolean {
        return typePool.firstIndexOf(clz) != -1
    }

    inline fun <reified T> isTypeRegistered(): Boolean {
        return typePool.firstIndexOf(T::class.java) != -1
    }

    /**
     * 添加数据到已有的数据源中
     *
     * @param index 添加的位置 (默认-1, 在最后添加)
     */
    fun addModels(models: List<*>) {
        addModels(models, -1)
    }

    fun addModels(models: List<*>, index: Int) {
        if (models.isEmpty()) return

        val adapterData = ArrayList(items)

        adapterData.apply {
            if (contains(State.Empty)) {
                val position = indexOf(State.Empty)
                remove(State.Empty)
                Handler().post {
                    items = this
                    notifyItemRemoved(position)
                }
            }
            if (contains(State.Fetching)) {
                val position = indexOf(State.Fetching)
                remove(State.Fetching)
                Handler().post {
                    items = this
                    notifyItemRemoved(position)
                }
            }
            if (contains(State.NoMore)) {
                val position = indexOf(State.NoMore)
                remove(State.NoMore)
                Handler().post {
                    items = this
                    notifyItemRemoved(position)
                }
            }
            if (contains(State.Loading)) {
                val position = indexOf(State.Loading)
                remove(State.Loading)
                Handler().post {
                    items = this
                    notifyItemRemoved(position)
                }
            }
        }

        // 如果移除掉状态后的 adapter data 为空
        // 则调用 setModels()
        if (adapterData.isEmpty()) {
            setModels(models)
            return
        }

        // 若 index 非 -1, real index 则为指定的值
        // 否则为去掉 EMPTY 后的
        val realIndex = if (index != -1) index else adapterData.size
        adapterData.addAll(realIndex, models)
        Handler().post {
            items = adapterData
            notifyItemInserted(realIndex)
        }
    }

    sealed class State {
        /** 空白列表 */
        object Empty : State()

        /** 初始化加载 */
        object Loading : State()

        /** 正在(预)加载 */
        object Fetching : State()

        /** 没有更多 */
        object NoMore : State()

        /** 出错 */
        object Error : State()

        /** 闲置 */
        object IDLE : State()
    }
}