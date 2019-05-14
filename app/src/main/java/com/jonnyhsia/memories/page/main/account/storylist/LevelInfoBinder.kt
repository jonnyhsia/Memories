package com.jonnyhsia.memories.page.main.account.storylist

import android.view.View
import com.arch.jonnyhsia.memories.model.passport.bean.LevelInfoModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.parse
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_level_info.*

class LevelInfoBinder(
        private val clickListener: View.OnClickListener
) : ItemBinder<LevelInfoModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_level_info

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        holder.itemView.setOnClickListener(clickListener)
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: LevelInfoModel) {
        with(holder) {
            tvLevelQuote.text = "\"${item.levelQuote}\""
            tvCurrentLevel.text = item.currentLevel
            tvNextLevel.text = item.nextLevel
            tvLevelUpCondition.text = item.levelUpCondition.parse()
        }
    }
}