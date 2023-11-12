package hh.school.lesson_6_zemskov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import hh.school.lesson_6_zemskov.databinding.FragmentPageBinding

private const val ARG_URL = "arg_url"
private const val ARG_CAPTION = "arg_caption"

class PageFragment : Fragment() {
    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    private var paramUrl: String? = null
    private var paramCaption: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramUrl = it.getString(ARG_URL)
            paramCaption = it.getString(ARG_CAPTION)
        }
    }

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
                arguments = Bundle().apply {
                    putString(ARG_URL, paramUrl)
                    putString(ARG_CAPTION, paramCaption)
                }
            }
    }
}