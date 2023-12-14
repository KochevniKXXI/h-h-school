package hh.school.lesson_12_zemskov.ui.details_bridge_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.FragmentDetailsBridgeBinding
import hh.school.lesson_12_zemskov.ui.BaseFragment
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import hh.school.lesson_12_zemskov.ui.model.BridgeState

class DetailsBridgeFragment : BaseFragment(R.layout.fragment_details_bridge) {

    private val binding by viewBinding(FragmentDetailsBridgeBinding::bind)
    private val args: DetailsBridgeFragmentArgs by navArgs()
    private val viewModel: DetailsBridgeViewModel by daggerViewModels()
    private val glideRequestManager by lazy { Glide.with(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInsets()
        initUi()
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUiState)
        if (savedInstanceState == null) {
            viewModel.loadBridgeById(args.bridgeId)
        }
    }

    override fun onTimeTickReceive() {
        binding.bridgeShortInfoView.updateIconBridgeState()
    }

    /**
     * Обрабатывет полученное [UiState].
     */
    private fun updateUiState(uiState: UiState<Bridge>) {
        when (uiState) {
            is UiState.Success -> {
                binding.bridgeShortInfoView.bridge =
                    uiState.data.copy(divorces = args.bridgeDivorces.toList())
                binding.textViewDescription.text = uiState.data.description
                binding.toolbarDetailsBridge.title = uiState.data.name
                val imageUrl = when (uiState.data.state) {
                    BridgeState.NORMAL, BridgeState.SOON -> uiState.data.photoCloseUrl
                    BridgeState.LATE -> uiState.data.photoOpenUrl
                }
                glideRequestManager.load(imageUrl)
                    .into(binding.imageViewExpandedToolbarBackground)
                binding.screenStateView.setLoadingState(false)
            }

            is UiState.Empty -> {
                binding.screenStateView.setErrorState(getString(R.string.message_bridge_not_data))
            }

            is UiState.Error -> {
                binding.screenStateView.setErrorState(
                    uiState.error.message,
                    getString(R.string.request_try_again)
                ) {
                    viewModel.loadBridgeById(args.bridgeId)
                }
            }

            is UiState.Loading -> {
                binding.screenStateView.setLoadingState(true)
            }
        }
    }

    /**
     * Обрабатывает входящие [WindowInsetsCompat] для [DetailsBridgeFragment].
     */
    private fun initInsets() {
        binding.collapsingToolbarLayout.setOnApplyWindowInsetsListener { _, windowInsets ->
            val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            binding.toolbarDetailsBridge.updatePadding(
                top = windowInsetsCompat.getInsets(
                    WindowInsetsCompat.Type.statusBars()
                ).top
            )
            binding.imageViewExpandedToolbarBackground.updateLayoutParams {
                height += windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
            }
            windowInsets
        }
    }

    /**
     * Инициализирует и настраивает пользовательский интерфейс [DetailsBridgeFragment].
     */
    private fun initUi() {
        binding.toolbarDetailsBridge.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}