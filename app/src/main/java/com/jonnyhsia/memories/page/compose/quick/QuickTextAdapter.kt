package com.jonnyhsia.memories.page.compose.quick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.page.compose.format.OnItemClickListener
import kotlinx.android.extensions.LayoutContainer

class QuickTextAdapter(
        private val onClickListener: OnItemClickListener<QuickText>
) : RecyclerView.Adapter<QuickTextAdapter.ViewHolder>() {

    var items: List<QuickText> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_quick_text, parent, false))
        holder.itemView.setOnClickListener {
            onClickListener(items[holder.adapterPosition], holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]
        val view = holder.itemView as TextView
        view.text = item.text
    }

    override fun getItemCount() = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer

    class QuickText(
            val text: String
    )
}