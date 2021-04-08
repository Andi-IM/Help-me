package id.airham.demo_start

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.airham.demo_start.databinding.ActivitySplashscreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startAnimation()
        loading()
    }

    private fun loading() {
        // menahan main thread untuk memberi delay selama 2000 ms
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }

    private fun startAnimation() {
        // membuat format animasi sesuai dengan keyframe
        binding.anim1.setImageResource(R.drawable.avd_anim)
        val animConvert : AnimatedVectorDrawable = binding.anim1.drawable as AnimatedVectorDrawable
        animConvert.start()

       /* val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        binding.anim1.animation = animation

        val animationOut = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
        binding.anim2.animation = animationOut*/
    }
}