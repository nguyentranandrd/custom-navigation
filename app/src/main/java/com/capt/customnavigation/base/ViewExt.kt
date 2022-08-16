package com.capt.customnavigation.base

import android.view.MotionEvent
import android.view.View

fun View.setClick(onClick: (v: View) -> Unit) {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.animate()
                    .scaleX(0.99F)
                    .scaleY(0.99F)
                    .alpha(0.5F)
                    .setDuration(100)
                    .start()
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.animate()
                    .scaleX(1F)
                    .scaleY(1F)
                    .alpha(1F)
                    .setDuration(100)
                    .start()
                v.performClick()
                v.invalidate()
            }
        }
        true
    }
    setOnClickListener {
        onClick(this)
    }
}