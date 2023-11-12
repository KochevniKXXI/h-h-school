package hh.school.lesson_6_zemskov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.button.MaterialButton
import hh.school.lesson_6_zemskov.databinding.FragmentThreeItemBinding

class ThreeItemFragment : Fragment() {

    private var _binding: FragmentThreeItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeItemBinding.inflate(layoutInflater, container, false)
        binding.buttonShowBanner.setOnClickListener {
            childFragmentManager.commit {
                setReorderingAllowed(true)
                (it as MaterialButton).text = if (binding.fragmentContainerViewPictures.isEmpty()) {
                    add<ViewPagerFragment>(R.id.fragmentContainerViewPictures)
                    resources.getString(R.string.button_show_banner_text_collapse)
                } else {
                    remove(binding.fragmentContainerViewPictures.getFragment())
                    resources.getString(R.string.button_show_banner_text)
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}