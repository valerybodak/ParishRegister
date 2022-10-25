package com.parish.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.parish.register.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animateLogoAndStartApp()
    }

    private fun animateLogoAndStartApp(){
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                startApp()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        alphaAnimation.duration = ANIMATION_DURATION
        alphaAnimation.startOffset = ANIMATION_DELAY
        alphaAnimation.fillAfter = true
        binding.logoContainer.startAnimation(alphaAnimation)
    }

    private fun startApp(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object{
        private const val ANIMATION_DELAY = 900L
        private const val ANIMATION_DURATION = 1000L
    }
}