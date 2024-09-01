package id.nisyafawwaz.nyampur.android.ui.main.saved

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.FragmentSortSavedRecipeBottomSheetBinding

class SortSavedBottomSheetFragment: BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.NyampurBottomSheetStyle

    private val binding: FragmentSortSavedRecipeBottomSheetBinding by lazy {
        FragmentSortSavedRecipeBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(): SortSavedBottomSheetFragment {
            return SortSavedBottomSheetFragment()
        }
    }
}