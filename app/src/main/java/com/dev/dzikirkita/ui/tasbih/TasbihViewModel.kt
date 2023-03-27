package com.dev.dzikirkita.ui.tasbih

import androidx.lifecycle.ViewModel

class TasbihViewModel : ViewModel() {
    var target = "33"
    var count = 0
    var isVibrateAlert = false
    var isSoundAlert = false

    fun increaseCount() {
        count++
    }

    fun resetCount() {
        count = 0
    }

    fun setTargetCount(target: String) {
        this.target = target
    }

    fun alertWithVibrate() {
        isVibrateAlert = !isVibrateAlert
    }

    fun alertWithSound() {
        isSoundAlert = !isSoundAlert
    }
}