package id.airham.demo_start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.airham.demo_start.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.create_account)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // Back key Pressing
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}