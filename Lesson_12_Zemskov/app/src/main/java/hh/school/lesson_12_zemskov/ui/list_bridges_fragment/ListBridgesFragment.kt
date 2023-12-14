package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.FragmentListBridgesBinding
import hh.school.lesson_12_zemskov.ui.BaseFragment
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import hh.school.lesson_12_zemskov.ui.views.BridgeShortInfoView

class ListBridgesFragment : BaseFragment(R.layout.fragment_list_bridges) {

    private val binding by viewBinding(FragmentListBridgesBinding::bind)
    private val viewModel: ListBridgesViewModel by daggerViewModels()
    private val bridgesAdapter = BridgesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInsets()
        initUi()
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUiState)
    }

    override fun onTimeTickReceive() {
        binding.recyclerViewBridges.children.forEach {
            (it as BridgeShortInfoView).updateIconBridgeState()
        }
    }

    /**
     * Обрабатывет полученное [UiState].
     */
    private fun updateUiState(uiState: UiState<List<Bridge>>) {
        when (uiState) {
            is UiState.Success -> {
                bridgesAdapter.submitBridges(uiState.data)
                binding.screenStateView.setLoadingState(false)
            }

            is UiState.Empty -> {
                binding.screenStateView.setErrorState(getString(R.string.message_list_bridges_empty))
            }

            is UiState.Error -> {
                binding.screenStateView.setErrorState(
                    uiState.error.message,
                    getString(R.string.request_try_again)
                ) {
                    viewModel.loadBridges()
                }
            }

            is UiState.Loading -> {
                binding.screenStateView.setLoadingState(true)
            }
        }
    }

    /**
     * Обрабатывает входящие [WindowInsetsCompat] для [ListBridgesFragment].
     */
    private fun initInsets() {
        binding.appBarLayout.setOnApplyWindowInsetsListener { appBar, windowInsets ->
            val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            appBar.updatePadding(
                top = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
            windowInsets
        }
    }

    /**
     * Инициализирует и настраивает пользовательский интерфейс [ListBridgesFragment].
     */
    private fun initUi() {
        binding.recyclerViewBridges.adapter = bridgesAdapter.apply {
            onItemClick = { bridgeId, bridgeDivorces ->
                val action = ListBridgesFragmentDirections
                    .actionListBridgesFragmentToDetailsBridgeFragment(
                        bridgeId,
                        bridgeDivorces.toTypedArray()
                    )
                findNavController().navigate(action)
            }
        }
        binding.toolbarListBridges.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemMap -> {
                    val action =
                        ListBridgesFragmentDirections.actionListBridgesFragmentToMapFragment()
                    findNavController().navigate(action)
                }
            }
            true
        }
    }
}