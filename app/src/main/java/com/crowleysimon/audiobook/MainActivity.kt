package com.crowleysimon.audiobook

import android.content.Context
import android.content.res.ColorStateList.valueOf
import android.content.res.Configuration.*
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.edit
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.screen_player.*

class MainActivity : AppCompatActivity() {

    private var progress: Float = 0f
    val handler = Handler()
    private val runnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                progress++
                progressBar3.setProgress(progress)
                if (progress < 100) {
                    handler.removeCallbacks(this)
                    handler.postDelayed(this, 16)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_player)

        imageView7.setOnClickListener { flipDayNightMode() }
        imageView8.setOnClickListener {
            imageView8.setImageDrawable(getDrawable(this, R.drawable.ic_bookmark_filled))
        }

        loadImage("https://covers.booktopia.com.au/big/9781760984120/7025/air-fryer-express.webp")

        progressBar3.setProgress(progress)
        handler.postDelayed(runnable, 16)

        imageView.setOnClickListener {
            loadImage("https://covers.booktopia.com.au/big/9780733340901/3242/loud.webp")
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .transition(withCrossFade())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.setImageBitmap(resource)
                    Palette.from(resource).generate { palette -> themePlayer(palette) }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun themePlayer(palette: Palette?) {
        if (palette == null) return
        imageView.outlineAmbientShadowColor = getBookColour(palette)
        imageView.outlineSpotShadowColor = getBookColour(palette)
        progressBar.progressTintList = valueOf(getBookColour(palette))
        progressBar3.setColor(getBookColour(palette))
    }

    private fun getBookColour(palette: Palette): Int =
        when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_NO -> palette.getDominantColor(Color.BLACK)
            UI_MODE_NIGHT_YES -> palette.getLightMutedColor(Color.BLACK)
            UI_MODE_NIGHT_UNDEFINED -> palette.getDominantColor(Color.BLACK)
            else -> palette.getDominantColor(Color.BLACK)
        }

    private fun flipDayNightMode() {
        getSharedPreferences("GENERAL", Context.MODE_PRIVATE).edit {
            putBoolean("NIGHT_MODE", (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_NO)
        }
        when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_NO -> delegate.localNightMode = MODE_NIGHT_YES
            else -> delegate.localNightMode = MODE_NIGHT_NO
        }
    }
}