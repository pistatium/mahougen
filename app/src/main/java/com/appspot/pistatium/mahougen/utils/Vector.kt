package com.appspot.pistatium.mahougen.utils

/**
 * Created by kimihiro on 2016/03/25.
 */
data class Vector(val x: Float, val y:Float) {

    operator fun plus(v : Vector) : Vector {
        return Vector(this.x + v.x, this.y + v.y)
    }

    operator fun minus(v : Vector) : Vector {
        return Vector(this.x - v.x, this.y - v.y)
    }

    operator fun times(r: Float): Vector {
        return Vector(this.x * r, this.y * r)
    }

    fun size(): Float {
        return Math.sqrt((this.x * this.x + this.y * this.y).toDouble()).toFloat()
    }

    fun angle(): Float {
        return Math.atan2(this.x.toDouble(), this.y.toDouble()).toFloat()
    }
    companion object {
        fun ofAngle(theta: Float): Vector {
            val td = theta.toDouble()
            return Vector(Math.cos(td).toFloat(), Math.sin(td).toFloat())
        }
    }
}
