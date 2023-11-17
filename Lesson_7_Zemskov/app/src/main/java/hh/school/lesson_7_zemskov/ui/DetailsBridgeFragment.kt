package hh.school.lesson_7_zemskov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.data.BridgesApiClient
import hh.school.lesson_7_zemskov.data.model.asInternalModel
import hh.school.lesson_7_zemskov.databinding.FragmentDetailsBridgeBinding
import hh.school.lesson_7_zemskov.extensions.asString
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
            parentFragmentManager.popBackStack()
        }

        binding.itemBridge.iconButtonReminder.visibility = View.GONE

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
        binding.layoutInfo.textViewInfo.visibility = View.GONE
        binding.layoutInfo.buttonRetry.visibility = View.GONE
        lifecycleScope.launch {
            delay(1_000)
            runCatching {
                BridgesApiClient.apiService.getBridge(id)
            }.onSuccess { networkBridge ->
                if (networkBridge.name == null) {
                    binding.itemBridge.root.visibility = View.GONE
                    binding.textViewDescription.visibility = View.GONE
                    binding.layoutInfo.textViewInfo.text =
                        getString(R.string.text_view_info_empty_list_bridges)
                    binding.layoutInfo.buttonRetry.text =
                        getString(R.string.button_retry_text_search)
                    binding.layoutInfo.textViewInfo.visibility = View.VISIBLE
                    binding.layoutInfo.buttonRetry.visibility = View.VISIBLE
                } else {
                    val bridge = networkBridge.asInternalModel()
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
                    binding.itemBridge.textViewDivorces.text = bridgeDivorces.asString()
                    binding.itemBridge.imageViewBridgeState.setImageResource(images.first)
                    binding.textViewDescription.text = bridge.description
                    binding.layoutInfo.textViewInfo.visibility = View.GONE
                    binding.layoutInfo.buttonRetry.visibility = View.GONE
                    binding.itemBridge.root.visibility = View.VISIBLE
                    binding.textViewDescription.visibility = View.VISIBLE
                }
            }.onFailure {
                binding.itemBridge.root.visibility = View.GONE
                binding.textViewDescription.visibility = View.GONE
                binding.layoutInfo.textViewInfo.text = it.message
                binding.layoutInfo.buttonRetry.text = getString(R.string.button_retry_text)
                binding.layoutInfo.textViewInfo.visibility = View.VISIBLE
                binding.layoutInfo.buttonRetry.visibility = View.VISIBLE
            }
            binding.layoutInfo.circularProgressIndicator.hide()
        }
    }
}