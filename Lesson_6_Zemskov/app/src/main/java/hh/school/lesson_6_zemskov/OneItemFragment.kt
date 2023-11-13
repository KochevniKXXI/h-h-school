package hh.school.lesson_6_zemskov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hh.school.lesson_6_zemskov.databinding.FragmentOneItemBinding

class OneItemFragment : Fragment() {

    private var _binding: FragmentOneItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneItemBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener { item ->
            Toast.makeText(this.context, item.title, Toast.LENGTH_SHORT).show()
            true
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}