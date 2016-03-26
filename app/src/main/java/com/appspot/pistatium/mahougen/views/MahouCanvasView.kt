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

    private var vertexCount = 12
    private var center: Vector = Vector(0f, 0f)
    private var paint: Paint = Paint()

    val pathArray: Array<Path>

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
        this.center = Vector(width / 2.0f, height / 2.0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathArray.forEach { p ->
            canvas.drawPath(p, paint)

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val current = Vector(event.x, event.y)
        val direction = current - this.center
        val r = direction.size()
        val alpha = (2.0 * Math.PI / this.vertexCount).toFloat()
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
                    p.moveTo(target)
                }
            }
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                for ((i, p) in this.pathArray.withIndex()) {
                    var target = this.center + Vector.ofAngle(theta + i * alpha) * r
                    p.lineTo(target)
                }
            }
        }
        invalidate()
        return true
    }

    fun configure(vertexCount: Int, paint: Paint? = null) {
        this.vertexCount = vertexCount
        paint?.let {
            this.paint = paint
        }
    }

    fun clear() {
        this.pathArray.forEach { p -> p.reset() }
        invalidate()
    }
}

fun Path.moveTo(v: Vector) {
    this.moveTo(v.x, v.y)
}

fun Path.lineTo(v: Vector) {
    this.lineTo(v.x, v.y)
}