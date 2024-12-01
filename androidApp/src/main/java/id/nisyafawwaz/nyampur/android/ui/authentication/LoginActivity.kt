package id.nisyafawwaz.nyampur.android.ui.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.ui.main.MainActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.getValue
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.removeExtraPaddingError
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.util.UUID

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    override val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun initViews() {
        setupStatusBar()
        binding.apply {
            tilEmail.removeExtraPaddingError()
            etEmail.doAfterTextChanged {
                when {
                    it.isBlank() -> {
                        tilEmail.apply {
                            isErrorEnabled = true
                            error = context.getString(R.string.error_email_empty)
                        }
                        btnContinue.isEnabled = false
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(it).matches() -> {
                        tilEmail.apply {
                            isErrorEnabled = true
                            error = context.getString(R.string.error_email_invalid)
                        }
                        btnContinue.isEnabled = false
                    }

                    else -> {
                        tilEmail.apply {
                            isErrorEnabled = false
                            error = null
                        }
                        btnContinue.isEnabled = true
                    }
                }
            }
        }
    }

    private fun setupStatusBar() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val navigationBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, navigationBars.bottom)
            insets
        }
    }

    override fun initListener() {
        with(binding) {
            btnContinue.onClick {
                authenticationViewModel.signInOtp(etEmail.getValue())
            }

            btnGoogleSignIn.onClick {
                val credential = CredentialManager.create(this@LoginActivity)

                val rawNonce = UUID.randomUUID().toString()
                val bytes = rawNonce.toByteArray(Charsets.UTF_8)
                val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
                val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

                val googleIdOptions =
                    GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId("112464382295-b1k1gkqetc1lip65d526ebagtflrukpp.apps.googleusercontent.com")
                        .setNonce(hashedNonce)
                        .build()

                val request =
                    GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOptions)
                        .build()

                lifecycleScope.launch {
                    try {
                        val result = credential.getCredential(this@LoginActivity, request)
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        authenticationViewModel.signInWithGoogle(googleIdToken)
                    } catch (e: GetCredentialException) {
                        Log.e(LoginActivity::class.simpleName, "initListener: $e")
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(LoginActivity::class.simpleName, "initListener: $e")
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    } catch (e: RestException) {
                        Log.e(LoginActivity::class.simpleName, "initListener: $e")
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e(LoginActivity::class.simpleName, "initListener: $e")
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun initObserver() {
        observeSendEmailOtp()
        observeSignInWithGoogle()
    }

    private fun observeSignInWithGoogle() {
        lifecycleScope.launch {
            authenticationViewModel.signInWithGoogleResult.collectLatest {
                when (it) {
                    ResultState.Loading -> binding.btnContinue.isEnabled = false
                    is ResultState.Error -> {
                        Log.e(LoginActivity::class.simpleName, it.error.message.orEmpty(), it.error)
                        Toast.makeText(
                            this@LoginActivity,
                            it.error.message.orEmpty(),
                            Toast.LENGTH_SHORT,
                        ).show()
                        binding.btnContinue.isEnabled = true
                    }

                    is ResultState.Success -> {
                        MainActivity.start(this@LoginActivity)
                        binding.btnContinue.isEnabled = true
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun observeSendEmailOtp() {
        lifecycleScope.launch {
            authenticationViewModel.signInOtpResult.collectLatest {
                when (it) {
                    ResultState.Loading -> {
                        binding.btnContinue.isEnabled = false
                    }

                    is ResultState.Error -> {
                        Log.e(LoginActivity::class.simpleName, it.error.message.orEmpty(), it.error)
                        Toast.makeText(
                            this@LoginActivity,
                            it.error.message.orEmpty(),
                            Toast.LENGTH_SHORT,
                        ).show()
                        binding.btnContinue.isEnabled = true
                    }

                    is ResultState.Success -> {
                        OtpActivity.start(this@LoginActivity, binding.etEmail.getValue())
                        binding.btnContinue.isEnabled = true
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            btnContinue.isEnabled = !etEmail.text.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()
        }
    }

    companion object {
        fun start(
            context: Context,
            isClearTask: Boolean = true,
        ) {
            Intent(context, LoginActivity::class.java).apply {
                if (isClearTask) {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }.also {
                context.startActivity(it)
            }
        }
    }
}
