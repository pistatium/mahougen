package com.appspot.pistatium.mahougen.utils

/**
 * Created by kimihiro on 2016/03/25.
 */
data class Vector(val x: Double, val y:Double) {

    operator fun plus(v : Vector) : Vector {
        return Vector(this.x + v.x, this.y + v.y)
    }

    operator fun minus(v : Vector) : Vector {
        return Vector(this.x - v.x, this.y - v.y)
    }

    operator fun times(r: Double): Vector {
        return Vector(this.x * r, this.y * r)
    }

    fun size(): Double {
        return Math.sqrt((this.x * this.x + this.y * this.y))
    }

    fun angle(): Double {
        return Math.atan2(this.x, this.y)
    }
    companion object {
        fun ofAngle(theta: Double): Vector {
            val td = theta.toDouble()
            return Vector(Math.cos(td), Math.sin(td))
        }
    }
}
