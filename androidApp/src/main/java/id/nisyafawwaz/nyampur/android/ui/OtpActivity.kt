package id.nisyafawwaz.nyampur.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OtpActivity::class.java)
            context.startActivity(intent)
        }
    }
}