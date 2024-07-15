package com.myapplication.customView.snow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myapplication.R
import com.myapplication.utils.Randomizer
import com.myapplication.utils.toBitmap

class SnowfallView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    private val snowflakesNum: Int
    private var snowflakeImage: Bitmap?
    private val snowflakeAlphaMin: Int
    private val snowflakeAlphaMax: Int
    private var snowflakeAngleMax: Int
    private val snowflakeSizeMinInPx: Int
    private val snowflakeSizeMaxInPx: Int
    private var snowflakeSpeedMin: Int
    private val snowflakeSpeedMax: Int
    private val snowflakesFadingEnabled: Boolean
    private val snowflakesAlreadyFalling: Boolean

    private var isCongratulationAnimation:Boolean = false

    private lateinit var updateSnowflakesThread: UpdateSnowflakesThread
    private var snowflakes: Array<Snowflake>? = null
    private var congratulationArray = intArrayOf(
        R.drawable.favourite,
        R.drawable.flower,
        R.drawable.zigzag,
        R.drawable.confetti,
        R.drawable.serpentine)



    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SnowfallView)
        try {
            snowflakesNum = a.getInt(R.styleable.SnowfallView_snowflakesNum, DEFAULT_SNOWFLAKES_NUM)
            snowflakeImage = a.getDrawable(R.styleable.SnowfallView_snowflakeImage)?.toBitmap()
            snowflakeAlphaMin = a.getInt(R.styleable.SnowfallView_snowflakeAlphaMin, DEFAULT_SNOWFLAKE_ALPHA_MIN)
            snowflakeAlphaMax = a.getInt(R.styleable.SnowfallView_snowflakeAlphaMax, DEFAULT_SNOWFLAKE_ALPHA_MAX)
            snowflakeAngleMax = a.getInt(R.styleable.SnowfallView_snowflakeAngleMax, DEFAULT_SNOWFLAKE_ANGLE_MAX)
            snowflakeSizeMinInPx = a.getDimensionPixelSize(R.styleable.SnowfallView_snowflakeSizeMin, dpToPx(DEFAULT_SNOWFLAKE_SIZE_MIN_IN_DP))
            snowflakeSizeMaxInPx = a.getDimensionPixelSize(R.styleable.SnowfallView_snowflakeSizeMax, dpToPx(DEFAULT_SNOWFLAKE_SIZE_MAX_IN_DP))
            snowflakeSpeedMin = a.getInt(R.styleable.SnowfallView_snowflakeSpeedMin, DEFAULT_SNOWFLAKE_SPEED_MIN)
            snowflakeSpeedMax = a.getInt(R.styleable.SnowfallView_snowflakeSpeedMax, DEFAULT_SNOWFLAKE_SPEED_MAX)
            snowflakesFadingEnabled = a.getBoolean(R.styleable.SnowfallView_snowflakesFadingEnabled, DEFAULT_SNOWFLAKES_FADING_ENABLED)
            snowflakesAlreadyFalling = a.getBoolean(R.styleable.SnowfallView_snowflakesAlreadyFalling, DEFAULT_SNOWFLAKES_ALREADY_FALLING)

            setLayerType(LAYER_TYPE_HARDWARE, null)

        } finally {
            a.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateSnowflakesThread = UpdateSnowflakesThread()
    }

    override fun onDetachedFromWindow() {
        updateSnowflakesThread.quit()
        super.onDetachedFromWindow()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        snowflakes = createSnowflakes(false,"")
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (changedView === this && visibility == GONE) {
            snowflakes?.forEach { it.reset() }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode) {
            return
        }

        var haveAtLeastOneVisibleSnowflake = false

        val localSnowflakes = snowflakes
        if (localSnowflakes != null) {
            for (snowflake in localSnowflakes) {
                if (snowflake.isStillFalling()) {
                    haveAtLeastOneVisibleSnowflake = true
                    snowflake.draw(canvas)
                }
            }
        }

        if (haveAtLeastOneVisibleSnowflake) {
            updateSnowflakes()
        } else {
            visibility = GONE
        }

        val fallingSnowflakes = snowflakes?.filter { it.isStillFalling() }
        if (fallingSnowflakes?.isNotEmpty() == true) {
            fallingSnowflakes.forEach { it.draw(canvas) }
            updateSnowflakes()
        }
        else {
            visibility = GONE
        }
    }

    fun stopFalling() {
        snowflakes?.forEach { it.shouldRecycleFalling = false }
    }

    fun restartFalling() {
        snowflakes?.forEach { it.shouldRecycleFalling = true }
    }

    fun setCongratulationAnimation(isCongoViewOn:Boolean){

        isCongratulationAnimation = isCongoViewOn
    }

    private fun spToPx(sp: Int): Float {
        return (sp * resources.displayMetrics.density)
    }
    private fun createSnowflakes(isText:Boolean,text:String): Array<Snowflake> {
        val randomizer = Randomizer()


        if(isCongratulationAnimation){

            val iconArray:ArrayList<Snowflake> = arrayListOf()

            for ( i in 0 until snowflakesNum){
                val param = Snowflake.Params(
                    parentWidth = width,
                    parentHeight = height,
                    image =  createBitmap(congratulationArray.random()),
                    alphaMin = snowflakeAlphaMin,
                    alphaMax = snowflakeAlphaMax,
                    angleMax = snowflakeAngleMax,
                    sizeMinInPx = snowflakeSizeMinInPx,
                    sizeMaxInPx = snowflakeSizeMaxInPx,
                    speedMin = snowflakeSpeedMin,
                    speedMax = snowflakeSpeedMax,
                    fadingEnabled = snowflakesFadingEnabled,
                    alreadyFalling = snowflakesAlreadyFalling)
                iconArray.add(Snowflake(randomizer, param))
            }
            return iconArray.toTypedArray()

        }else if(isText){
            val snowflakeParams = Snowflake.Params(
                parentWidth = width,
                parentHeight = height,
                image =  snowflakeImage,
                alphaMin = snowflakeAlphaMin,
                alphaMax = snowflakeAlphaMax,
                angleMax = snowflakeAngleMax,
                sizeMinInPx = snowflakeSizeMinInPx,
                sizeMaxInPx = snowflakeSizeMaxInPx,
                speedMin = snowflakeSpeedMin,
                speedMax = snowflakeSpeedMax,
                fadingEnabled = snowflakesFadingEnabled,
                alreadyFalling = snowflakesAlreadyFalling,
                isText = true,
                text = text,
                textSize = spToPx(32)
                )
            return Array(snowflakesNum) { Snowflake(randomizer, snowflakeParams) }
        }else{
            val snowflakeParams = Snowflake.Params(
                parentWidth = width,
                parentHeight = height,
                image =  snowflakeImage,
                alphaMin = snowflakeAlphaMin,
                alphaMax = snowflakeAlphaMax,
                angleMax = snowflakeAngleMax,
                sizeMinInPx = snowflakeSizeMinInPx,
                sizeMaxInPx = snowflakeSizeMaxInPx,
                speedMin = snowflakeSpeedMin,
                speedMax = snowflakeSpeedMax,
                fadingEnabled = snowflakesFadingEnabled,
                alreadyFalling = snowflakesAlreadyFalling)
            return Array(snowflakesNum) { Snowflake(randomizer, snowflakeParams) }
        }




    }


    private fun updateSnowflakes() {

        updateSnowflakesThread.handler.post {
            var haveAtLeastOneVisibleSnowflake = false

            val localSnowflakes = snowflakes ?: return@post

            for (snowflake in localSnowflakes) {
                if (snowflake.isStillFalling()) {
                    haveAtLeastOneVisibleSnowflake = true
                    snowflake.update()
                }
            }

            if (haveAtLeastOneVisibleSnowflake) {
                postInvalidateOnAnimation()
            }
        }
    }

    private class UpdateSnowflakesThread : HandlerThread("SnowflakesComputations") {
        val handler: Handler
        init {
             start()
            handler = Handler(looper)
        }
    }

    fun setFallingIcon(icon:Int){
        snowflakeImage =   getDrawable(context,icon)?.toBitmap()
    }

    fun createBitmap(icon:Int):Bitmap{
        return getDrawable(context,icon)?.toBitmap()!!
    }
  fun changeAngle(angle:Int){

      snowflakeAngleMax = angle
  }
    fun changeSpeed(speed:Int){
        snowflakeSpeedMin = speed
    }

    fun createFallingIconArray(isText:Boolean = false,text:String =" "){
        snowflakes = createSnowflakes(isText,text)
    }

    companion object {
        private const val DEFAULT_SNOWFLAKES_NUM = 200
        private const val DEFAULT_SNOWFLAKE_ALPHA_MIN = 150
        private const val DEFAULT_SNOWFLAKE_ALPHA_MAX = 250
        private const val DEFAULT_SNOWFLAKE_ANGLE_MAX = 10
        private const val DEFAULT_SNOWFLAKE_SIZE_MIN_IN_DP = 2
        private const val DEFAULT_SNOWFLAKE_SIZE_MAX_IN_DP = 8
        private const val DEFAULT_SNOWFLAKE_SPEED_MIN = 2
        private const val DEFAULT_SNOWFLAKE_SPEED_MAX = 8
        private const val DEFAULT_SNOWFLAKES_FADING_ENABLED = false
        private const val DEFAULT_SNOWFLAKES_ALREADY_FALLING = false
    }
}