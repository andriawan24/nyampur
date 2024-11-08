package id.nisyafawwaz.nyampur.android.ui.authentication

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityOtpBinding
import id.nisyafawwaz.nyampur.android.ui.main.MainActivity
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.disable
import id.nisyafawwaz.nyampur.android.utils.extensions.getCompatColorList
import id.nisyafawwaz.nyampur.android.utils.extensions.gone
import id.nisyafawwaz.nyampur.android.utils.extensions.hideKeyboard
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickWithThrottle
import id.nisyafawwaz.nyampur.android.utils.extensions.setNavigationBarInset
import id.nisyafawwaz.nyampur.android.utils.extensions.visible
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : BaseActivity<ActivityOtpBinding>() {
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    private var otpValue = emptyString()
    private var email = emptyString()
    private val textInputOtpLayouts: List<TextInputLayout> by lazy {
        listOf(
            binding.tilInputCode1,
            binding.tilInputCode2,
            binding.tilInputCode3,
            binding.tilInputCode4,
            binding.tilInputCode5,
            binding.tilInputCode6,
        )
    }

    override fun initIntent() {
        email = intent.getStringExtra(EXTRA_EMAIL).orEmpty()
    }

    override fun initViews() {
        enableEdgeToEdge()
        binding.root.setNavigationBarInset()
        binding.tvEmail.text = email
        setupForm(textInputOtpLayouts)
    }

    private fun setupForm(textInputLayouts: List<TextInputLayout>) {
        textInputLayouts.forEachIndexed { index, textInputLayout ->
            textInputLayout.editText?.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus && binding.tvErrorOtp.isVisible) {
                        setErrorMessage(null)
                    }
                }

            textInputLayout.editText?.doAfterTextChanged {
                otpValue =
                    textInputLayouts.joinToString(separator = emptyString()) { otp ->
                        otp.editText?.text.toString()
                    }
                if (it.toString().length == 1) {
                    textInputLayout.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_otp_stroke_color))
                    if (index == textInputLayouts.lastIndex || otpValue.length == textInputLayouts.size) {
                        hideKeyboard()
                        textInputLayout.editText?.clearFocus()
                    } else {
                        textInputLayouts.getOrNull(index + 1)?.editText?.requestFocus()
                    }
                } else if (it.toString().isEmpty()) {
                    textInputLayout.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_stroke_color))
                    textInputLayouts.getOrNull(index - 1)?.requestFocus()
                }
                binding.btnContinue.isEnabled = otpValue.length == textInputLayouts.size
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
                val focusedInputIndex =
                    textInputOtpLayouts.indexOfFirst {
                        it.editText?.isFocused == true
                    }

                if (textInputOtpLayouts[focusedInputIndex].editText?.text.toString().isNotBlank()) {
                    return super.dispatchKeyEvent(event)
                } else {
                    val textInput = textInputOtpLayouts.getOrNull(focusedInputIndex - 1)
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

            btnContinue.onClickWithThrottle {
                authenticationViewModel.validateEmailOtp(otpValue, email)
            }
        }
    }

    override fun initObserver() {
        authenticationViewModel.validateEmailOtpResult.observeLiveData(
            this,
            onLoading = {
                showLoading()
            },
            onSuccess = {
                hideLoading()
                MainActivity.start(this@OtpActivity)
            },
            onFailure = {
                setErrorMessage(it.message.orEmpty())
                hideLoading()
            },
        )
    }

    private fun hideLoading() {
        binding.pbLoading.gone()
    }

    private fun showLoading() {
        binding.apply {
            pbLoading.visible()
            btnContinue.disable()
        }
    }

    private fun setErrorMessage(error: String? = null) {
        if (!error.isNullOrBlank()) {
            binding.tvErrorOtp.apply {
                visible()
                text = error
            }
            textInputOtpLayouts.forEach {
                it.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_error_stroke_color))
            }
        } else {
            binding.tvErrorOtp.gone()
            textInputOtpLayouts.forEach {
                it.setBoxStrokeColorStateList(getCompatColorList(R.color.selector_text_input_layout_otp_stroke_color))
            }
        }
    }

    companion object {
        private const val EXTRA_EMAIL = "extra_email"

        fun start(
            context: Context,
            email: String,
        ) {
            val intent =
                Intent(context, OtpActivity::class.java).apply {
                    putExtra(EXTRA_EMAIL, email)
                }
            context.startActivity(intent)
        }
    }
}
