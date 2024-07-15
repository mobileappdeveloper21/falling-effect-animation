package com.myapplication.customView.confettiView

import android.graphics.Color

enum class ConfettiParticleEnum{
    RECTANGLE,
    CIRCLE,
}

data class ConfettiParticle(
    var x: Float,
    var y: Float,
    var dx: Int,
    var dy: Int,
    var alpha:Int,
    var color: Int = Color.BLUE,
    var shape:ConfettiParticleEnum = ConfettiParticleEnum.CIRCLE
) {

}