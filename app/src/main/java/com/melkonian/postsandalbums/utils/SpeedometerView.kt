package com.melkonian.postsandalbums.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.melkonian.postsandalbums.R

class SpeedometerView : View {
    companion object {
        private const val START_ANGLE_FIRST_SECTION = 135f
        private const val SWEEP_ANGLE_FIRST_SECTION = 90f
        private const val START_ANGLE_SECOND_SECTION = 225f
        private const val SWEEP_ANGLE_SECOND_SECTION = 100f
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
    private var colorMainCenterCircle: Int = Color.WHITE
    private var colorCenterCircle: Int = context.getColor(R.color.center_circle_color)
    private var colorPointerLine: Int = context.getColor(R.color.pointer_line_color)

    private val paintFirstSection = Paint()
    private val paintSecondSection = Paint()
    private val paintThirdSection = Paint()

    private val pointerLine = Paint()
    private val paintBottomCenterPointerCircle = Paint()
    private val paintTopCenterPointerCircle = Paint()

    private var paddingMain = 0f
    private var paddingInnerCircle = 0f

    private var pointerLineRotateDegree = 0f
    private var strokePointerLineWidth = 4.5f

    private var x = 0f
    private var y = 0f
    private var constantMeasure = 0f
    private var isWidthBiggerThanHeight = false

    private var internalArcStrokeWidthScale = 0.215
    private var paddingInnerCircleScale = 0.27
    private var pointerLineStrokeWidthScale = 0.006944
    private val mainCircleScale = 5f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        x = width.toFloat()
        y = height.toFloat()

        if (x >= y) {
            constantMeasure = y
            isWidthBiggerThanHeight = true
        } else {
            constantMeasure = x
            isWidthBiggerThanHeight = false
        }

        internalArcStrokeWidth = (constantMeasure * internalArcStrokeWidthScale).toFloat()
        paddingInnerCircle = (constantMeasure * paddingInnerCircleScale).toFloat()
        strokePointerLineWidth = (constantMeasure * pointerLineStrokeWidthScale).toFloat()

        paintFirstSection.apply {
            style = Paint.Style.STROKE
            strokeWidth = internalArcStrokeWidth
            color = colorFirstSection
            isAntiAlias = true
        }

        paintSecondSection.apply {
            style = Paint.Style.STROKE
            strokeWidth = internalArcStrokeWidth
            color = colorSecondSection
            isAntiAlias = true
        }

        paintThirdSection.apply {
            style = Paint.Style.STROKE
            strokeWidth = internalArcStrokeWidth
            color = colorThirdSection
            isAntiAlias = true
        }

        val rectfInner: RectF = if (isWidthBiggerThanHeight) {
            RectF(
                (x - constantMeasure) / 2 + paddingInnerCircle,
                paddingInnerCircle,
                (x - constantMeasure) / 2 + paddingInnerCircle + constantMeasure - 2
                        * paddingInnerCircle,
                constantMeasure - paddingInnerCircle
            )
        } else {
            RectF(
                paddingInnerCircle,
                (y - constantMeasure) / 2 + paddingInnerCircle,
                constantMeasure - paddingInnerCircle,
                (y - constantMeasure) / 2
                        + constantMeasure - paddingInnerCircle
            )
        }

        canvas.drawArc(
            rectfInner,
            START_ANGLE_FIRST_SECTION,
            SWEEP_ANGLE_FIRST_SECTION,
            false,
            paintFirstSection
        )
        canvas.drawArc(
            rectfInner,
            START_ANGLE_SECOND_SECTION,
            SWEEP_ANGLE_SECOND_SECTION,
            false,
            paintSecondSection
        )
        canvas.drawArc(
            rectfInner,
            START_ANGLE_THIRD_SECTION,
            SWEEP_ANGLE_THIRD_SECTION,
            false,
            paintThirdSection
        )

        // pointer line START
        pointerLine.apply {
            color = colorPointerLine
            strokeWidth = strokePointerLineWidth
            isAntiAlias = true
        }
        canvas.rotate(pointerLineRotateDegree, x / 2, y / 2)

        val mainCircleStroke = (mainCircleScale * constantMeasure / 60).toInt()
        val a = 8
        if (isWidthBiggerThanHeight) {
            val stopX =
                (x - constantMeasure) / 2 + paddingInnerCircle + constantMeasure - 2 * paddingInnerCircle + mainCircleStroke
            val stopY = y / 2
            val path = Path()
            path.fillType = Path.FillType.EVEN_ODD
            path.moveTo(x / 2 + mainCircleStroke / a, y / 2 - mainCircleStroke)
            path.lineTo(x / 2 + mainCircleStroke / a, y / 2 + mainCircleStroke)
            path.lineTo(stopX, stopY)
            path.close()
            canvas.drawPath(path, pointerLine)
        } else {
            val stopX = constantMeasure - paddingInnerCircle + mainCircleStroke
            val stopY = y / 2
            val path = Path()
            path.fillType = Path.FillType.EVEN_ODD
            path.moveTo(x / 2 + mainCircleStroke / a, y / 2 - mainCircleStroke)
            path.lineTo(x / 2 + mainCircleStroke / a, y / 2 + mainCircleStroke)
            path.lineTo(stopX, stopY)
            path.close()
            canvas.drawPath(path, pointerLine)
        }

        paintBottomCenterPointerCircle.apply {
            style = Paint.Style.FILL
            color = colorCenterCircle
            isAntiAlias = true
        }
        canvas.drawCircle(x / 2, y / 2, mainCircleStroke.toFloat(), paintBottomCenterPointerCircle)

        paintTopCenterPointerCircle.apply {
            style = Paint.Style.FILL
            color = colorMainCenterCircle
        }
        canvas.drawCircle(x / 2, y / 2, mainCircleStroke.toFloat() / 2, paintTopCenterPointerCircle)
    }

    fun setInternalArcStrokeWidth(internalArcStrokeWidth: Float) {
        this.internalArcStrokeWidth = internalArcStrokeWidth
        invalidate()
    }

    fun setColorFirstSection(colorFirstItem: Int) {
        this.colorFirstSection = colorFirstItem
        invalidate()
    }

    fun setColorSecondSection(colorSecondItem: Int) {
        this.colorSecondSection = colorSecondItem
        invalidate()
    }

    fun setColorThirdSection(colorThirdItem: Int) {
        this.colorThirdSection = colorThirdItem
        invalidate()
    }

    fun setColorCenterCircle(colorCenterCircle: Int) {
        this.colorCenterCircle = colorCenterCircle
        invalidate()
    }

    fun setColorMainCenterCircle(colorMainCenterCircle: Int) {
        this.colorMainCenterCircle = colorMainCenterCircle
        invalidate()
    }

    fun setColorPointerLine(colorPointerLine: Int) {
        this.colorPointerLine = colorPointerLine
        invalidate()
    }

    fun setPaddingMain(paddingMain: Float) {
        this.paddingMain = paddingMain
        invalidate()
    }

    fun setPaddingInnerCircle(paddingInnerCircle: Float) {
        this.paddingInnerCircle = paddingInnerCircle
        invalidate()
    }

    fun setPointerLineRotateDegree(rotateDegree: Float) {
        this.pointerLineRotateDegree = rotateDegree
        invalidate()
    }

    fun setStrokePointerLineWidth(strokePointerLineWidth: Float) {
        this.strokePointerLineWidth = strokePointerLineWidth
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

    fun setConstantMeasure(constantMeasure: Float) {
        this.constantMeasure = constantMeasure
        invalidate()
    }

    fun setWidthBiggerThanHeight(isWidthBiggerThanHeight: Boolean) {
        this.isWidthBiggerThanHeight = isWidthBiggerThanHeight
        invalidate()
    }

    fun setInternalArcStrokeWidthScale(internalArcStrokeWidthScale: Double) {
        this.internalArcStrokeWidthScale = internalArcStrokeWidthScale
        invalidate()
    }

    fun setPaddingInnerCircleScale(paddingInnerCircleScale: Double) {
        this.paddingInnerCircleScale = paddingInnerCircleScale
        invalidate()
    }

    fun setPointerLineStrokeWidthScale(pointerLineStrokeWidthScale: Double) {
        this.pointerLineStrokeWidthScale = pointerLineStrokeWidthScale
        invalidate()
    }

    fun getInternalArcStrokeWidth(): Float {
        return internalArcStrokeWidth
    }

    fun getColorFirstItem(): Int {
        return colorFirstSection
    }

    fun getColorSecondItem(): Int {
        return colorSecondSection
    }

    fun getColorThirdItem(): Int {
        return colorThirdSection
    }

    fun getColorCenterCircle(): Int {
        return colorCenterCircle
    }

    fun getColorMainCenterCircle(): Int {
        return colorMainCenterCircle
    }

    fun getColorPointerLine(): Int {
        return colorPointerLine
    }

    fun getPaddingMain(): Float {
        return paddingMain
    }

    fun getPaddingInnerCircle(): Float {
        return paddingInnerCircle
    }

    fun getRotateDegree(): Float {
        return pointerLineRotateDegree
    }

    fun getStrokePointerLineWidth(): Float {
        return strokePointerLineWidth
    }

    override fun getX(): Float {
        return x
    }

    override fun getY(): Float {
        return y
    }

    fun getConstantMeasure(): Float {
        return constantMeasure
    }

    fun isWidthBiggerThanHeight(): Boolean {
        return isWidthBiggerThanHeight
    }

    fun getInternalArcStrokeWidthScale(): Double {
        return internalArcStrokeWidthScale
    }

    fun getPaddingInnerCircleScale(): Double {
        return paddingInnerCircleScale
    }

    fun getPointerLineStrokeWidthScale(): Double {
        return pointerLineStrokeWidthScale
    }
}