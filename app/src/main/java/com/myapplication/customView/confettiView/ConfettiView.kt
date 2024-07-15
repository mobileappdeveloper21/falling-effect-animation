package com.myapplication.customView.confettiView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.myapplication.R
import com.myapplication.customView.partical.Particle
import com.myapplication.utils.Randomizer
import kotlin.random.Random

class ConfettiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr),SurfaceHolder.Callback {

    val randomizer = Randomizer()
    private val particles = mutableListOf<ConfettiParticle>()
    private var hasSurface: Boolean = false
    private var hasSetup = false

    private var particleCount =100

    // Paints
    private val paintParticles: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 2F
    }

    private var surfaceViewThread:SurfaceViewThread? =null
    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        if (holder != null) holder.addCallback(this)
        hasSurface = false
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ConfettiView,
            defStyleAttr,
            0
        )

        try {

        }catch (e:Exception){

        }finally {
            typedArray.recycle()
        }


    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        hasSurface = true
        startAnimation()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = null
    }

    inner class SurfaceViewThread:Thread(){

        private var running = true
        private var canvas: Canvas? = null

        override fun run() {
            createConfettiParticle()
            while (running){
                try {
                    canvas = holder.lockCanvas()
                    synchronized(holder){
                        // Clear Screen every frame
                        canvas?.drawColor(Color.WHITE, PorterDuff.Mode.SRC)

                        for(i in 0 until particleCount){
                            particles[i].x =randomizer.randomDouble(width).toFloat()
                            particles[i].y ++

                            if (particles[i].x < 0) {
                                particles[i].x = width.toFloat()
                            } else if (particles[i].x > width) {
                                particles[i].x = 0F
                            }

                            if (particles[i].y < 0) {
                                particles[i].y = height.toFloat()
                            } else if (particles[i].y > height) {
                                particles[i].y = 0F
                            }


                            paintParticles.alpha = particles[i].alpha
                            canvas?.drawCircle(particles[i].x, particles[i].y, 30.0f, paintParticles)
                        }

                    }

                }catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
            }

        }

        fun requestExitAndWait(){
            running = false

            try {
                join()
            }catch (e:InterruptedException){

            }
        }
    }

    private fun createConfettiParticle() {
        if (!hasSetup) {
            hasSetup = true
            particles.clear()
            for(i in 0 until particleCount){

                particles.add(
                    ConfettiParticle(
                        x = 0f ,
                        y = 0f,
                        dx = Random.nextInt(-2, 2),
                        dy = Random.nextInt(-2, 2),
                        alpha = Random.nextInt(150, 255),
                    )
                )
            }

        }

    }


    public fun startAnimation(){

        if(hasSurface){

            if (surfaceViewThread == null) {
                surfaceViewThread = SurfaceViewThread()
            }

            surfaceViewThread?.start()
        }

    }

}