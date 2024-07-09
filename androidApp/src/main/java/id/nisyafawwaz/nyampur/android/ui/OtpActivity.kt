package id.nisyafawwaz.nyampur.android.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityOtpBinding
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.getCompatColor
import id.nisyafawwaz.nyampur.android.utils.extensions.getCompatColorList
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
                binding.tilInputCode1,
                binding.tilInputCode2,
                binding.tilInputCode3,
                binding.tilInputCode4
            )
        )
    }

    private fun setupForm(textInputLayouts: List<TextInputLayout>) {
        textInputLayouts.forEachIndexed { index, textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                otpValue = textInputLayouts.joinToString(separator = emptyString()) { otp ->
                    otp.editText?.text.toString()
                }

                if (it.toString().length == 1) {
                    textInputLayout.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_otp_stroke_color))
                    if (index == textInputLayouts.lastIndex || otpValue.length == 4) {
                        hideKeyboard()
                        textInputLayout.editText?.clearFocus()
                    } else {
                        textInputLayouts.getOrNull(index + 1)?.editText?.requestFocus()
                    }
                } else if (it.toString().isEmpty()) {
                    textInputLayout.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_stroke_color))
                    textInputLayouts.getOrNull(index - 1)?.requestFocus()
                }
                Log.d(OtpActivity::class.simpleName, "setupForm: ${otpValue}")
                binding.btnContinue.isEnabled = otpValue.length == 4
            }

            textInputLayout.editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    textInputLayouts.getOrNull(index + 1)?.editText?.requestFocus()
                } else if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                }

                true
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
            if (binding.etInputCode1.isFocused) {
                return super.dispatchKeyEvent(event)
            } else {
                val textInputLayouts = listOf(
                    binding.tilInputCode1,
                    binding.tilInputCode2,
                    binding.tilInputCode3,
                    binding.tilInputCode4
                )
                val focusedInputIndex = textInputLayouts.indexOfFirst {
                    it.editText?.isFocused == true
                }

                if (textInputLayouts[focusedInputIndex].editText?.text.toString().isNotBlank()) {
                    return super.dispatchKeyEvent(event)
                } else {
                    val textInput = textInputLayouts.getOrNull(focusedInputIndex - 1)
                    textInput?.editText?.setText(emptyString())
                    textInput?.requestFocus()
                    return true
                }
            }
        } else {
            return super.dispatchKeyEvent(event)
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