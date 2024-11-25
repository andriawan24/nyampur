package id.nisyafawwaz.nyampur.android.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityProfileBinding
import id.nisyafawwaz.nyampur.android.ui.authentication.LoginActivity
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.disable
import id.nisyafawwaz.nyampur.android.utils.extensions.enable
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.ui.AccountManager
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    private val accountManager: AccountManager by inject()
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        binding.apply {
            tvName.text = accountManager.getCurrentUser()?.userMetadata?.getOrDefault("name", emptyString()).toString()
            tvEmail.text = accountManager.getCurrentUser()?.email.orEmpty()
        }
    }

    override fun initListener() {
        binding.btnSignOut.onClick {
            AlertDialog.Builder(this)
                .setTitle(R.string.title_sign_out)
                .setMessage(R.string.message_sign_out)
                .setPositiveButton(R.string.action_no) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.action_yes) { dialog, _ ->
                    dialog.dismiss()
                    authenticationViewModel.signOut()
                }
                .show()
        }
    }

    override fun initObserver() {
        super.initObserver()
        authenticationViewModel.signOutResult.observeLiveData(
            lifecycleOwner = this,
            onLoading = {
                binding.btnSignOut.disable()
            },
            onSuccess = {
                binding.btnSignOut.enable()
                LoginActivity.start(this)
            },
            onFailure = {
                binding.btnSignOut.enable()
                Snackbar.make(binding.root, it.message.orEmpty(), Snackbar.LENGTH_SHORT).show()
            },
        )
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
}
