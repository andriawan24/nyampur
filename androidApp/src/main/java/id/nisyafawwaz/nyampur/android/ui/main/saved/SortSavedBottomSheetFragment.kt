package id.nisyafawwaz.nyampur.android.ui.main.saved

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.FragmentSortSavedRecipeBottomSheetBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.utils.enums.SortType

class SortSavedBottomSheetFragment : BottomSheetDialogFragment() {
    private val binding: FragmentSortSavedRecipeBottomSheetBinding by lazy {
        FragmentSortSavedRecipeBottomSheetBinding.inflate(layoutInflater)
    }

    private var onSortChanged: ((type: SortType) -> Unit)? = null
    private var currentlySelected = SortType.RECENTLY
    private val sortRadios by lazy {
        listOf(
            binding.radioRecentlyAdded,
            binding.radioLevel,
            binding.radioMinutesToMake,
            binding.radioAToZ,
            binding.radioZToA,
        )
    }
    private var currentlyCheckedRadio: AppCompatRadioButton? = null

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
        initProcess()
        initActionListener()
    }

    private fun initProcess() {
        sortRadios.getOrNull(SortType.entries.indexOf(currentlySelected))?.isChecked = true
    }

    private fun initActionListener() {
        binding.btnClose.onClick { dismiss() }
        sortRadios.forEach { sortRadio ->
            sortRadio.setOnCheckedChangeListener { _, _ ->
                currentlyCheckedRadio?.isChecked = false
                currentlyCheckedRadio = sortRadio
                onSortChanged?.invoke(SortType.entries.getOrNull(sortRadios.indexOf(sortRadio)) ?: SortType.RECENTLY)
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(
            currentlySelected: SortType = SortType.RECENTLY,
            onSortChanged: ((type: SortType) -> Unit)?,
        ): SortSavedBottomSheetFragment {
            return SortSavedBottomSheetFragment().apply {
                this.onSortChanged = onSortChanged
                this.currentlySelected = currentlySelected
            }
        }
    }
}
