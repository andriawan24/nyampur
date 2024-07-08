package id.nisyafawwaz.nyampur.android.ui

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.material.textfield.TextInputEditText
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityOtpBinding
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.getValue
import id.nisyafawwaz.nyampur.android.utils.extensions.hideKeyboard
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickThrottle
import id.nisyafawwaz.nyampur.android.utils.extensions.setNavigationBarInset

class OtpActivity : BaseActivity<ActivityOtpBinding>() {

    override val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    private var otpValue = emptyString()

    override fun initIntent() = Unit

    override fun initViews() {
        enableEdgeToEdge()
        binding.root.setNavigationBarInset()
        setupForm(
            listOf(
                binding.etInputCode1,
                binding.etInputCode2,
                binding.etInputCode3,
                binding.etInputCode4
            )
        )
    }

    private fun setupForm(editTexts: List<TextInputEditText>) {
        editTexts.forEachIndexed { index, editText ->
            editText.doAfterTextChanged {
                otpValue = editTexts.joinToString { otp -> otp.getValue() }
                if (it.length == 1) {
                    if (index == editTexts.lastIndex || otpValue.length == 4) {
                        hideKeyboard()
                        editText.clearFocus()
                    } else {
                        editTexts.getOrNull(index + 1)?.requestFocus()
                    }
                } else if (it.isEmpty()) {
                    editTexts.getOrNull(index - 1)?.requestFocus()
                }

                binding.btnContinue.isEnabled = otpValue.length == 4
            }

            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    editTexts.getOrNull(index + 1)?.requestFocus()
                } else if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                }

                true
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                finish()
            }

            btnContinue.onClickThrottle {
                Toast.makeText(this@OtpActivity, "Clicked continue", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OtpActivity::class.java)
            context.startActivity(intent)
        }
    }
}