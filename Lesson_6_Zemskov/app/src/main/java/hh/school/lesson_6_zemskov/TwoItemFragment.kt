package hh.school.lesson_6_zemskov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hh.school.lesson_6_zemskov.adapters.ServiceItemDecoration
import hh.school.lesson_6_zemskov.adapters.ServicesAdapter
import hh.school.lesson_6_zemskov.data.DataSource
import hh.school.lesson_6_zemskov.databinding.FragmentTwoItemBinding

class TwoItemFragment : Fragment() {

    private var _binding: FragmentTwoItemBinding? = null
    private val binding get() = _binding!!
    private val servicesAdapter = ServicesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoItemBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).addMenu(R.menu.item_two_screen_actions, viewLifecycleOwner)

        binding.recyclerView.addItemDecoration(
            ServiceItemDecoration(R.dimen.services_divider_size)
        )
        binding.recyclerView.adapter = servicesAdapter
        servicesAdapter.setList(DataSource.listServices)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}