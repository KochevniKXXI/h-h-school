package hh.school.lesson_7_zemskov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.data.BridgesApiClient
import hh.school.lesson_7_zemskov.data.model.NetworkBridge
import hh.school.lesson_7_zemskov.data.model.asInternalModel
import hh.school.lesson_7_zemskov.databinding.FragmentDetailsBridgeBinding
import hh.school.lesson_7_zemskov.model.Divorce
import hh.school.lesson_7_zemskov.utils.Time
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

private const val ARG_BRIDGE_ID = "arg_bridge_id"
private const val ARG_BRIDGE_DIVORCES = "arg_bridge_divorces"

class DetailsBridgeFragment : Fragment() {

    private var _binding: FragmentDetailsBridgeBinding? = null
    private val binding get() = _binding!!

    private val bridgeId by lazy { arguments?.getInt(ARG_BRIDGE_ID) ?: 0 }
    private val bridgeDivorces by lazy {
        arguments?.getParcelableArray(ARG_BRIDGE_DIVORCES)?.map { it as Divorce } ?: listOf()
    }

    companion object {
        @JvmStatic
        fun newInstance(bridgeId: Int, bridgeDivorces: List<Divorce>) =
            DetailsBridgeFragment().apply {
                arguments = bundleOf(
                    ARG_BRIDGE_ID to bridgeId,
                    ARG_BRIDGE_DIVORCES to bridgeDivorces.toTypedArray()
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBridgeBinding.inflate(layoutInflater, container, false)
        binding.collapsingToolbarLayout.setOnApplyWindowInsetsListener { _, windowInsets ->
            binding.toolbarDetailsBridge.updatePadding(top = windowInsets.systemWindowInsetTop)
            binding.imageViewExpandedToolbarBackground.updateLayoutParams {
                height += windowInsets.systemWindowInsetTop
            }
            windowInsets
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarDetailsBridge.setNavigationOnClickListener {
            (activity as? Navigator)?.popBackStack()
        }

        binding.itemBridge.iconButtonReminder.isVisible = false

        binding.layoutInfo.circularProgressIndicator.setVisibilityAfterHide(View.GONE)

        binding.layoutInfo.buttonRetry.setOnClickListener {
            loadBridge(bridgeId)
        }

        loadBridge(bridgeId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadBridge(id: Int) {
        binding.layoutInfo.circularProgressIndicator.show()
        binding.layoutInfo.textViewInfo.isVisible = false
        binding.layoutInfo.buttonRetry.isVisible = false
        lifecycleScope.launch {
            delay(1_000)
            runCatching {
                BridgesApiClient.apiService.getBridge(id)
            }.onSuccess { networkBridge ->
                if (networkBridge.name == null) {
                    bindEmpty()
                } else {
                    bindBridge(networkBridge)
                }
            }.onFailure {
                bindError(it)
            }
            binding.layoutInfo.circularProgressIndicator.hide()
        }
    }

    private fun bindError(it: Throwable) {
        binding.itemBridge.root.isVisible = false
        binding.textViewDescription.isVisible = false
        binding.layoutInfo.textViewInfo.text = it.message
        binding.layoutInfo.buttonRetry.text = getString(R.string.button_retry_text)
        binding.layoutInfo.textViewInfo.isVisible = true
        binding.layoutInfo.buttonRetry.isVisible = true
    }

    private fun bindBridge(networkBridge: NetworkBridge) {
        val bridge = networkBridge.asInternalModel().copy(divorces = bridgeDivorces)
        val calendar = Calendar.getInstance()
        val currentTime =
            Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        val divorces = bridgeDivorces.map {
            try {
                Time.parse(it.start)..Time.parse(it.end)
            } catch (e: Exception) {
                null
            }
        }
        val images = if (divorces.any { it != null && currentTime in it }) {
            R.drawable.ic_brige_late to networkBridge.photoOpenUrl
        } else if (divorces.any {
                it != null && currentTime.until(it.start) <= Time(
                    1,
                    0
                )
            }) {
            R.drawable.ic_brige_soon to networkBridge.photoOpenUrl
        } else {
            R.drawable.ic_brige_normal to networkBridge.photoCloseUrl
        }

        Glide.with(requireContext()).load(images.second)
            .into(binding.imageViewExpandedToolbarBackground)
        binding.collapsingToolbarLayout.title = bridge.name
        binding.itemBridge.textViewName.text = bridge.name
        binding.itemBridge.textViewDivorces.text = bridge.getDivorcesAsString()
        binding.itemBridge.imageViewBridgeState.setImageResource(images.first)
        binding.textViewDescription.text = bridge.description
        binding.layoutInfo.textViewInfo.isVisible = false
        binding.layoutInfo.buttonRetry.isVisible = false
        binding.itemBridge.root.isVisible = true
        binding.textViewDescription.isVisible = true
    }

    private fun bindEmpty() {
        binding.itemBridge.root.isVisible = false
        binding.textViewDescription.isVisible = false
        binding.layoutInfo.textViewInfo.text =
            getString(R.string.text_view_info_empty_list_bridges)
        binding.layoutInfo.buttonRetry.text =
            getString(R.string.button_retry_text_search)
        binding.layoutInfo.textViewInfo.isVisible = true
        binding.layoutInfo.buttonRetry.isVisible = true
    }
}