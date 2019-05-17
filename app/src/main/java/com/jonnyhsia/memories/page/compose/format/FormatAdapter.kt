package com.jonnyhsia.memories.page.compose.format

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.ui.ext.tooltipTextCompat
import com.jonnyhsia.appcore.ext.load
import com.jonnyhsia.memories.R
import kotlinx.android.extensions.LayoutContainer

typealias OnItemClickListener<T> = (item: T, position: Int) -> Unit

class FormatAdapter(
        private val onClickListener: OnItemClickListener<Format>
) : RecyclerView.Adapter<FormatAdapter.ViewHolder>() {

    private val items = arrayOf(
            Format.H1(), Format.H2(), Format.H3(), Format.List(), Format.Link(), Format.Quote()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_format, parent, false))
        holder.itemView.setOnClickListener {
            onClickListener(items[holder.adapterPosition], holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]
        val view = holder.itemView as ImageView
        view.load(item.iconRes)
        view.tooltipTextCompat = item.description

//        val textView = holder.itemView as TextLabel
//        textView.text = item.text
//        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(textView.context, item.iconRes), null, null, null)
    }

    override fun getItemCount() = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer

    sealed class Format(
            val flag: Array<String>,
            val text: String,
            val description: String,
            val iconRes: Int
    ) {

        //        class Bold : Format(flag = arrayOf("**", "**"), text = "粗体", description = "使用粗体文本", iconRes = R.drawable.ic_editor_link_24dp)
        class Quote :
                Format(flag = arrayOf(">"), text = "引用", description = "使用引用文本", iconRes = R.drawable.ic_editor_quote_0_24dp)

        class H1 :
                Format(flag = arrayOf("#"), text = "标题1", description = "1号标题", iconRes = R.drawable.ic_editor_h1_24dp)

        class H2 :
                Format(flag = arrayOf("##"), text = "标题2", description = "2号标题", iconRes = R.drawable.ic_editor_h2_24dp)

        class H3 :
                Format(flag = arrayOf("###"), text = "标题3", description = "3号标题", iconRes = R.drawable.ic_editor_h3_24dp)

        class List :
                Format(flag = arrayOf("-"), text = "列表", description = "使用无序列表", iconRes = R.drawable.ic_editor_list_0_24dp)

        class Link :
                Format(flag = arrayOf("[", "](", ")"), text = "链接", description = "插入链接", iconRes = R.drawable.ic_editor_link_24dp)
    }
}