package com.jonnyhsia.appcore

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.jonnyhsia.appcore.ext.load
import com.zhihu.matisse.engine.ImageEngine

class GlideV4Engine : ImageEngine {

    override fun loadImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        load(imageView, uri)
    }

    override fun loadThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        load(imageView, uri)
    }

    override fun loadGifImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        load(imageView, uri)
    }

    override fun loadGifThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        load(imageView, uri)
    }

    override fun supportAnimatedGif(): Boolean {
        return false
    }

    private fun load(imageView: ImageView, uri: Uri?) {
        imageView.load(uri) {
            centerCrop()
        }
    }
}