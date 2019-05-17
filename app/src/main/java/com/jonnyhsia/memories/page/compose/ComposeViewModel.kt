package com.jonnyhsia.memories.page.compose

import androidx.lifecycle.MutableLiveData
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.memories.page.compose.quick.QuickTextAdapter

class ComposeViewModel : BaseViewModel() {

    val quickTexts = MutableLiveData<List<QuickTextAdapter.QuickText>>()

    init {
        quickTexts.value = listOf(
                QuickTextAdapter.QuickText("Hello World"),
                QuickTextAdapter.QuickText("高能的土豆"),
                QuickTextAdapter.QuickText("ゼルダの伝説　ブレスオブザワイルド"),
                QuickTextAdapter.QuickText("Android"),
                QuickTextAdapter.QuickText("你妈儿子死了"),
                QuickTextAdapter.QuickText("Samsung Galaxy SX"),
                QuickTextAdapter.QuickText("Material Design"),
                QuickTextAdapter.QuickText("泰厉害勒"),
                QuickTextAdapter.QuickText("xmsl"),
                QuickTextAdapter.QuickText("快捷输入文字"),
                QuickTextAdapter.QuickText("添加按钮还没加"),
                QuickTextAdapter.QuickText("土豆牛鞭"),
                QuickTextAdapter.QuickText("🐮🍺"),
                QuickTextAdapter.QuickText("159****8262"),
                QuickTextAdapter.QuickText("5aSP5bu66LGq"),
                QuickTextAdapter.QuickText("Nintendo")
        )
    }
}
