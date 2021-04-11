package id.airham.demo_start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.airham.demo_start.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

    // Back key Pressing
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}