package com.jonnyhsia.memories.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arch.jonnyhsia.memories.model.trophy.bean.Trophy
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.memories.R

fun Context.showTrophyToast(trophy: Trophy) {
    val toast = Toast.makeText(this, "", Toast.LENGTH_LONG).apply {
        view = View.inflate(this@showTrophyToast, R.layout.toast_trophy, null)
        view.findViewById<TextView>(R.id.tvTrophyTitle).text = trophy.name
        setGravity(Gravity.TOP, 0, 20.dp)
    }
    toast.show()
}