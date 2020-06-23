package com.crowleysimon.audiobook

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_chapter_progress.view.*
import kotlin.math.roundToInt

class ChapterProgressView : LinearLayout {

    companion object {
        private const val STEP_AMOUNT = 100 / 3.5
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.view_chapter_progress, this)
    }

    fun setProgress(progress: Float) {
        firstProgressBar.progress = when {
            progress <= STEP_AMOUNT -> (progress / STEP_AMOUNT * 100).roundToInt()
            else -> 100
        }

        secondProgressBar.progress = when {
            progress <= STEP_AMOUNT -> 0
            progress > STEP_AMOUNT && progress <= STEP_AMOUNT * 2 -> ((progress - STEP_AMOUNT) / STEP_AMOUNT * 100).roundToInt()
            else -> 100
        }

        thirdProgressBar.progress = when {
            progress <= STEP_AMOUNT * 2 -> 0
            progress > STEP_AMOUNT * 2 && progress <= STEP_AMOUNT * 3 -> ((progress - (STEP_AMOUNT * 2)) / STEP_AMOUNT * 100).roundToInt()
            else -> 100
        }

        fourthProgressBar.progress = when {
            progress <= STEP_AMOUNT * 3 -> 0
            else -> ((progress - (STEP_AMOUNT * 3)) / (STEP_AMOUNT / 2) * 100).roundToInt()
        }
    }

    fun setColor(color: Int) {
        firstProgressBar.progressTintList = ColorStateList.valueOf(color)
        secondProgressBar.progressTintList = ColorStateList.valueOf(color)
        thirdProgressBar.progressTintList = ColorStateList.valueOf(color)
        fourthProgressBar.progressTintList = ColorStateList.valueOf(color)
    }


}