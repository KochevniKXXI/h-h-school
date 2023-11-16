package hh.school.lesson_7_zemskov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.data.BridgesApiClient
import hh.school.lesson_7_zemskov.databinding.FragmentListBridgesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListBridgesFragment : Fragment() {

    private var _binding: FragmentListBridgesBinding? = null
    private val binding get() = _binding!!

    private val bridgesAdapter = BridgesAdapter()

    companion object {
        @JvmStatic
        fun newInstance() = ListBridgesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBridgesBinding.inflate(layoutInflater, container, false)

        binding.toolbarListBridges.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.updatePadding(top = windowInsets.systemWindowInsetTop)
            windowInsets
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutInfo.circularProgressIndicator.setVisibilityAfterHide(View.GONE)

        binding.recyclerViewBridges.adapter = bridgesAdapter.apply {
            onClick = { id, bridgeDivorces ->
                id?.let { bridgeId ->
                    parentFragmentManager.commit {
                        add(
                            R.id.fragmentContainerViewMain,
                            DetailsBridgeFragment.newInstance(bridgeId, bridgeDivorces)
                        )
                        addToBackStack(null)
                    }
                }
            }
        }
        loadBridges()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadBridges() {
        binding.layoutInfo.circularProgressIndicator.show()
        lifecycleScope.launch {
            delay(1_000)
            runCatching {
                BridgesApiClient.apiService.getBridges()
            }.onSuccess {
                if (it.isEmpty()) {
                    binding.recyclerViewBridges.visibility = View.GONE
                    binding.layoutInfo.textViewInfo.text =
                        getString(R.string.text_view_info_empty_list_bridges)
                    binding.layoutInfo.buttonRetry.text =
                        getString(R.string.button_retry_text_search)
                    binding.layoutInfo.textViewInfo.visibility = View.VISIBLE
                    binding.layoutInfo.buttonRetry.visibility = View.VISIBLE
                } else {
                    bridgesAdapter.submitBridges(it)
                    binding.layoutInfo.textViewInfo.visibility = View.GONE
                    binding.layoutInfo.buttonRetry.visibility = View.GONE
                    binding.recyclerViewBridges.visibility = View.VISIBLE
                }
            }.onFailure {
                childFragmentManager.commit {
                    binding.recyclerViewBridges.visibility = View.GONE
                    binding.layoutInfo.textViewInfo.text = it.message
                    binding.layoutInfo.buttonRetry.text = getString(R.string.button_retry_text)
                    binding.layoutInfo.textViewInfo.visibility = View.VISIBLE
                    binding.layoutInfo.buttonRetry.visibility = View.VISIBLE
                }
            }
            binding.layoutInfo.circularProgressIndicator.hide()
        }
    }
}