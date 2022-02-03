package com.technzone.bai3.ui.base.views.appviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.technzone.bai3.R


class CustomBottomNavigationView : BottomNavigationView {
    private var mPath: Path? = null
    private var mPaint: Paint? = null

    // the coordinates of the first curve
    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    private val mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()

    //the coordinates of the second curve
    private var mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()

    private var rect: RectF? = null
    private var borderRadius = 0f

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val mNavigationBarWidth = width
        val mNavigationBarHeight = height
        mPath = Path()
        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint?.color = ContextCompat.getColor(context, R.color.white);
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun composeRoundedRectPath(
        rect: RectF,
        topLeftDiameter: Float,
        topRightDiameter: Float,
        bottomRightDiameter: Float,
        bottomLeftDiameter: Float
    ) {
        var topLeftDiameter = topLeftDiameter
        var topRightDiameter = topRightDiameter
        var bottomRightDiameter = bottomRightDiameter
        var bottomLeftDiameter = bottomLeftDiameter
        topLeftDiameter = if (topLeftDiameter < 0) 0f else topLeftDiameter
        topRightDiameter = if (topRightDiameter < 0) 0f else topRightDiameter
        bottomLeftDiameter = if (bottomLeftDiameter < 0) 0f else bottomLeftDiameter
        bottomRightDiameter = if (bottomRightDiameter < 0) 0f else bottomRightDiameter
        mPath?.moveTo(0 + topLeftDiameter / 2, 0f)
        mPath?.lineTo(0 - topRightDiameter / 2, 0f)
        mPath?.quadTo(rect.right, rect.top, rect.right, rect.top + topRightDiameter / 2)
        mPath?.lineTo(rect.right, rect.bottom - bottomRightDiameter / 2)
        mPath?.quadTo(rect.right, rect.bottom, rect.right - bottomRightDiameter / 2, rect.bottom)
        mPath?.lineTo(rect.left + bottomLeftDiameter / 2, rect.bottom)
        mPath?.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftDiameter / 2)
        mPath?.lineTo(rect.left, rect.top + topLeftDiameter / 2)
        mPath?.quadTo(rect.left, rect.top, rect.left + topLeftDiameter / 2, rect.top)
//        path.close()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        val mNavigationBarWidth = width
        val mNavigationBarHeight = height

        // the coordinates (x,y) of the start point before curve
        /* the CURVE_CIRCLE_RADIUS represent the radius of the fab button */
        val CURVE_CIRCLE_RADIUS = (resources.getDimension(R.dimen._40sdp) / 2).toInt()
        mFirstCurveStartPoint[mNavigationBarWidth / 2 - CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS / 2] =
            0
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint[mNavigationBarWidth / 2] = CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 2
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint[mNavigationBarWidth / 2 + CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS / 2] =
            0

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1[mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4] =
            mFirstCurveStartPoint.y
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2[mFirstCurveEndPoint.x - resources.getDimension(R.dimen._30sdp)
            .toInt()] = mFirstCurveEndPoint.y
        mSecondCurveControlPoint1[mSecondCurveStartPoint.x + resources.getDimension(R.dimen._30sdp)
            .toInt()] = mSecondCurveStartPoint.y
        mSecondCurveControlPoint2[mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4)] =
            mSecondCurveEndPoint.y
//        composeRoundedRectPath(
//            RectF(0f, 0f, mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat()),
//            120f,120f,0f,0f
//        )
        mPath?.reset()
        mPath!!.moveTo(0f, 0f)

        mPath!!.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())
        mPath!!.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat()
        )
        mPath!!.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat()
        )

        mPath!!.lineTo(mNavigationBarWidth.toFloat(), 0f)
        mPath!!.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        mPath!!.lineTo(0f, mNavigationBarHeight.toFloat())
        mPath!!.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mPath!!, mPaint!!)
    }

    fun roundedCornersDrawable(borderRadius: Float) {
        val p = mPaint
        p?.isAntiAlias = true
        val mNavigationBarWidth = width
        val mNavigationBarHeight = height
        rect = RectF(0f, 0f, mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        this.borderRadius = if (borderRadius < 0) 0.15f * Math.min(
            mNavigationBarWidth,
            mNavigationBarHeight
        ) else borderRadius
    }

}