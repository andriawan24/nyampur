package id.nisyafawwaz.nyampur.android.ui.camera

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentGalleryItemBinding
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString

class GalleryItemFragment : Fragment() {
    private var binding: FragmentGalleryItemBinding? = null
    private var imagePath = emptyString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGalleryItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivPhoto?.setImageURI(Uri.parse(imagePath))
    }

    companion object {
        fun newInstance(imagePath: String): GalleryItemFragment {
            return GalleryItemFragment().apply {
                this.imagePath = imagePath
            }
        }
    }
}
