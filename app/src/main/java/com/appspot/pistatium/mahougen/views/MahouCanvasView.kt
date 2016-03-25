package com.appspot.pistatium.mahougen.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by kimihiro on 16/03/25.
 */
class MahouCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint
    private val path: Path

    init {
        this.path = Path()
        this.paint = Paint()
        paint.color = Color.BLACK
        this.paint.style = Paint.Style.STROKE
        this.paint.isAntiAlias = true
        this.paint.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> this.path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> this.path.lineTo(x, y)
            MotionEvent.ACTION_UP -> this.path.lineTo(x, y)
        }
        invalidate()
        return true
    }

    fun delete() {
        this.path.reset()
        invalidate()
    }
}
