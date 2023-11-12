package hh.school.lesson_6_zemskov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import hh.school.lesson_6_zemskov.adapters.PicturesFragmentPagerAdapter
import hh.school.lesson_6_zemskov.data.DataSource
import hh.school.lesson_6_zemskov.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        val picturesAdapter = PicturesFragmentPagerAdapter(childFragmentManager, this.lifecycle)
        binding.viewPager.adapter = picturesAdapter
        picturesAdapter.setList(DataSource.listPictures)
        TabLayoutMediator(binding.tabLayoutDotIndicator, binding.viewPager) { _, _ -> }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}