package hh.school.lesson_6_zemskov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import hh.school.lesson_6_zemskov.databinding.FragmentPageBinding

private const val ARG_URL = "arg_url"
private const val ARG_CAPTION = "arg_caption"

class PageFragment : Fragment() {
    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    private val paramUrl by lazy { arguments?.getString(ARG_URL) }
    private val paramCaption by lazy { arguments?.getString(ARG_CAPTION) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPageBinding.inflate(layoutInflater, container, false)
        Glide
            .with(this)
            .load(paramUrl)
            .into(binding.imageViewPicture)
        binding.textViewCaption.text = paramCaption
        binding.cardViewPicture.setOnClickListener {
            (requireActivity() as OnPictureClickListener).onPictureClick(paramCaption)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(paramUrl: String, paramCaption: String) =
            PageFragment().apply {
                arguments = bundleOf(
                    ARG_URL to paramUrl,
                    ARG_CAPTION to paramCaption
                )
            }
    }
}