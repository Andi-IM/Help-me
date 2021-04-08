package id.airham.demo_start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.airham.demo_start.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        binding.btnSignUp.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}