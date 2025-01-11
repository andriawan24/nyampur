package id.nisyafawwaz.nyampur.android.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.FragmentConfirmationBottomSheetBinding
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick

class ConfirmationBottomSheet : BottomSheetDialogFragment() {
    private var title = emptyString()
    private var message = emptyString()
    private var buttonPositiveTitle = emptyString()
    private var buttonNegativeTitle = emptyString()

    private val binding: FragmentConfirmationBottomSheetBinding by lazy {
        FragmentConfirmationBottomSheetBinding.inflate(layoutInflater)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogStyle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initViews()
        initActionListener()
    }

    private fun initViews() {
        binding.apply {
            tvTitle.text = title
            tvMessage.text = message
            btnPositive.text = buttonPositiveTitle
            btnNegative.text = buttonNegativeTitle
        }
    }

    private fun initActionListener() {
        binding.apply {
            btnPositive.onClick {
                setFragmentResult(KEY_CONFIRMATION_REQUEST, bundleOf(KEY_CONFIRMATION_RETURN to true))
                dismiss()
            }
            btnNegative.onClick {
                setFragmentResult(KEY_CONFIRMATION_REQUEST, bundleOf(KEY_CONFIRMATION_RETURN to false))
                dismiss()
            }
        }
    }

    companion object {
        const val KEY_CONFIRMATION_REQUEST = "KEY_CONFIRMATION_REQUEST"
        const val KEY_CONFIRMATION_RETURN = "KEY_CONFIRMATION_RETURN"

        fun newInstance(
            title: String,
            message: String,
            buttonNegativeTitle: String,
            buttonPositiveTitle: String,
        ): ConfirmationBottomSheet {
            return ConfirmationBottomSheet().apply {
                this.title = title
                this.message = message
                this.buttonPositiveTitle = buttonPositiveTitle
                this.buttonNegativeTitle = buttonNegativeTitle
            }
        }
    }
}
