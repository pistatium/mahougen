package com.appspot.pistatium.mahougen.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat

import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.appspot.pistatium.mahougen.R
import com.appspot.pistatium.mahougen.utils.Vector

/**
 * Created by kimihiro on 16/03/25.
 */
class MahouCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var vertexCount = 10
    private var center: Vector = Vector(0.0, 0.0)
    private var paint: Paint = Paint()

    var pathArray: Array<Path>

    init {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.background))
        this.pathArray = Array(this.vertexCount, { i -> Path()})
        this.paint.color = Color.WHITE
        this.paint.style = Paint.Style.STROKE
        this.paint.isAntiAlias = true
        this.paint.strokeWidth = 3f
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        this.center = Vector(width / 2.0, height / 2.0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathArray.forEach { p ->
            canvas.drawPath(p, paint)

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val current = Vector(event.x.toDouble(), event.y.toDouble())
        val direction = current - this.center
        val r = direction.size()
        val alpha = (2.0 * Math.PI / this.vertexCount.toDouble())
        var theta = direction.angle()
        for (i in 0 .. this.vertexCount) {
            if (i * alpha > theta) {
                theta -= (i - 1) * alpha
                break
            }
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                for ((i, p) in this.pathArray.withIndex()) {
                    var target = this.center + Vector.ofAngle(theta + i * alpha) * r
                    if (vertexCount % 2 == 0 && i % 2 == 0) {
                        target = this.center + Vector.ofAngle(-theta + (i + 1) * alpha) * r
                    }
                    p.moveTo(target)
                }
            }
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                for ((i, p) in this.pathArray.withIndex()) {
                    var target = this.center + Vector.ofAngle(theta + i * alpha) * r
                    if (vertexCount % 2 == 0 &&i % 2 == 0) {
                        target = this.center + Vector.ofAngle(-theta + (i + 1) * alpha) * r
                    }
                    p.lineTo(target)
                }
            }
        }
        invalidate()
        return true
    }

    fun configure(vertexCount: Int, paint: Paint? = null) {
        this.vertexCount = vertexCount
        pathArray = Array(this.vertexCount, { i -> Path()})
        paint?.let {
            this.paint = paint
        }
        invalidate()
    }

    fun clear() {
        this.pathArray.forEach { p -> p.reset() }
        invalidate()
    }
}

fun Path.moveTo(v: Vector) {
    this.moveTo(v.x.toFloat(), v.y.toFloat())
}

fun Path.lineTo(v: Vector) {
    this.lineTo(v.x.toFloat(), v.y.toFloat())
}