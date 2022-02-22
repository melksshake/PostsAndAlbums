package com.melkonian.postsandalbums.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.melkonian.postsandalbums.R

class SpeedometerView : View {
    companion object {
        private const val START_ANGLE_FIRST_SECTION = 135f
        private const val SWEEP_ANGLE_FIRST_SECTION = 91f
        private const val START_ANGLE_SECOND_SECTION = 225f
        private const val SWEEP_ANGLE_SECOND_SECTION = 91f
        private const val START_ANGLE_THIRD_SECTION = 315f
        private const val SWEEP_ANGLE_THIRD_SECTION = 90f
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var internalArcStrokeWidth = 0f

    private var colorFirstSection: Int = context.getColor(R.color.first_section_color)
    private var colorSecondSection: Int = context.getColor(R.color.second_section_color)
    private var colorThirdSection: Int = context.getColor(R.color.third_section_color)
    private var colorNeedleCenterCircle: Int = Color.WHITE
    private var colorNeedleCircle: Int = context.getColor(R.color.center_circle_color)
    private var colorNeedleLine: Int = context.getColor(R.color.pointer_line_color)

    private val paintFirstSection = Paint()
    private val paintSecondSection = Paint()
    private val paintThirdSection = Paint()

    private val paintNeedleLine = Paint()
    private val paintBottomNeedleCircle = Paint()
    private val paintTopNeedleCircle = Paint()

    private val needlePath1 = Path().apply {
        fillType = Path.FillType.EVEN_ODD
    }
    private val needlePath2 = Path().apply {
        fillType = Path.FillType.EVEN_ODD
    }

    private var speedometerInnerCircle = 0f

    private var needleLineRotateDegree = 0f
    private var strokeNeedleLineWidth = 4.5f

    private var x = 0f
    private var y = 0f
    private var constantMeasure = 0f
    private var isWidthBiggerThanHeight = false

    private var internalArcStrokeWidthScale = 0.25 //thickness of the speedometer
    private var speedometerInnerCircleScale = 0.25 // scale of inner speedometer circle
    private var needleLineStrokeWidthScale = 0.006944
    private val needleCircleScale = 5f

    init {
        paintFirstSection.apply {
            style = Paint.Style.STROKE
            color = colorFirstSection
            isAntiAlias = true
        }

        paintSecondSection.apply {
            style = Paint.Style.STROKE
            color = colorSecondSection
            isAntiAlias = true
        }

        paintThirdSection.apply {
            style = Paint.Style.STROKE
            color = colorThirdSection
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        x = width.toFloat()
        y = height.toFloat()

        constantMeasure = minOf(x, y)
        isWidthBiggerThanHeight = x >= y

        internalArcStrokeWidth = (constantMeasure * internalArcStrokeWidthScale).toFloat()
        speedometerInnerCircle = (constantMeasure * speedometerInnerCircleScale).toFloat()
        strokeNeedleLineWidth = (constantMeasure * needleLineStrokeWidthScale).toFloat()

        paintFirstSection.strokeWidth = internalArcStrokeWidth
        paintSecondSection.strokeWidth = internalArcStrokeWidth
        paintThirdSection.strokeWidth = internalArcStrokeWidth

        drawSpeedometerSections(canvas)
        drawSpeedometerNeedle(canvas)
    }

    private fun drawSpeedometerSections(canvas: Canvas) {
        val speedometerBgSectionOval: RectF = getSpeedometerSectionOval(isWidthBiggerThanHeight)
        canvas.drawArc(
            speedometerBgSectionOval,
            START_ANGLE_FIRST_SECTION,
            SWEEP_ANGLE_FIRST_SECTION,
            false,
            paintFirstSection
        )
        canvas.drawArc(
            speedometerBgSectionOval,
            START_ANGLE_SECOND_SECTION,
            SWEEP_ANGLE_SECOND_SECTION,
            false,
            paintSecondSection
        )
        canvas.drawArc(
            speedometerBgSectionOval,
            START_ANGLE_THIRD_SECTION,
            SWEEP_ANGLE_THIRD_SECTION,
            false,
            paintThirdSection
        )
    }

    private fun getSpeedometerSectionOval(isWidthBiggerThanHeight: Boolean): RectF {
        return if (isWidthBiggerThanHeight) {
            RectF(
                (x - constantMeasure) / 2 + speedometerInnerCircle,
                speedometerInnerCircle,
                (x - constantMeasure) / 2 + speedometerInnerCircle + constantMeasure - 2 * speedometerInnerCircle,
                constantMeasure - speedometerInnerCircle
            )
        } else {
            RectF(
                speedometerInnerCircle,
                (y - constantMeasure) / 2 + speedometerInnerCircle,
                constantMeasure - speedometerInnerCircle,
                (y - constantMeasure) / 2 + constantMeasure - speedometerInnerCircle
            )
        }
    }

    private fun drawSpeedometerNeedle(canvas: Canvas) {
        paintNeedleLine.apply {
            color = colorNeedleLine
            strokeWidth = strokeNeedleLineWidth
            isAntiAlias = true
        }
        canvas.rotate(needleLineRotateDegree, x / 2, y / 2)

        val needleCircleStroke = (needleCircleScale * constantMeasure / 60).toInt()
        val a = 10
        if (isWidthBiggerThanHeight) {
            val stopX =
                (x - constantMeasure) / 2 + speedometerInnerCircle + constantMeasure - 2 * speedometerInnerCircle + needleCircleStroke
            val stopY = y / 2

            needlePath1.moveTo(x / 2 + needleCircleStroke / a, y / 2 - needleCircleStroke)
            needlePath1.lineTo(x / 2 + needleCircleStroke / a, y / 2 + needleCircleStroke)
            needlePath1.lineTo(stopX, stopY)
            needlePath1.close()
            canvas.drawPath(needlePath1, paintNeedleLine)
            needlePath1.rewind()
        } else {
            val stopX = constantMeasure - speedometerInnerCircle + needleCircleStroke
            val stopY = y / 2

            needlePath2.moveTo(x / 2 + needleCircleStroke / a, y / 2 - needleCircleStroke)
            needlePath2.lineTo(x / 2 + needleCircleStroke / a, y / 2 + needleCircleStroke)
            needlePath2.lineTo(stopX, stopY)
            needlePath2.close()
            canvas.drawPath(needlePath2, paintNeedleLine)
            needlePath2.rewind()
        }

        paintBottomNeedleCircle.apply {
            style = Paint.Style.FILL
            color = colorNeedleCircle
            isAntiAlias = true
        }
        canvas.drawCircle(x / 2, y / 2, needleCircleStroke.toFloat(), paintBottomNeedleCircle)

        paintTopNeedleCircle.apply {
            style = Paint.Style.FILL
            color = colorNeedleCenterCircle
        }
        canvas.drawCircle(x / 2, y / 2, needleCircleStroke.toFloat() / 2, paintTopNeedleCircle)
    }

    fun setPointerLineRotateDegree(rotateDegree: Float) {
        this.needleLineRotateDegree = rotateDegree
        invalidate()
    }

    override fun setX(x: Float) {
        this.x = x
        invalidate()
    }

    override fun setY(y: Float) {
        this.y = y
        invalidate()
    }

    override fun getX(): Float {
        return x
    }

    override fun getY(): Float {
        return y
    }
}