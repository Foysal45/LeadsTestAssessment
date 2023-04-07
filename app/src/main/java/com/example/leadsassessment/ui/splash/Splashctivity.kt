package com.example.leadsassessment.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.leadsassessment.MainActivity
import com.example.leadsassessment.R
import com.example.leadsassessment.databinding.ActivitySplashBinding


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        goToHome()


        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.bounce)

        //SplashScreen Logo Animation
        val backgroundImage: ImageView = findViewById(R.id.leads_corporation_logo)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce2)
        backgroundImage.startAnimation(slideAnimation)


        //SplashScreen Text Animation
        val txtAirPassengerAid: TextView = findViewById(R.id.welcome_text)
        txtAirPassengerAid.startAnimation(animation)


        val txtMinistryOfCivilAviationAndTourism: TextView = findViewById(R.id.leads_corporation_text)
        Handler(Looper.getMainLooper()).postDelayed({
            txtMinistryOfCivilAviationAndTourism.visibility = View.VISIBLE
            txtMinistryOfCivilAviationAndTourism.startAnimation(animation2)
        }, 1500)


    }


    private fun goToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500L)

    }

}