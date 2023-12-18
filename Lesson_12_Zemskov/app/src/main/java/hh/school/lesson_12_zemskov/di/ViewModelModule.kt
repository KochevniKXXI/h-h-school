package hh.school.lesson_12_zemskov.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hh.school.lesson_12_zemskov.ui.details_bridge_fragment.DetailsBridgeViewModel
import hh.school.lesson_12_zemskov.ui.list_bridges_fragment.ListBridgesViewModel
import hh.school.lesson_12_zemskov.ui.map_fragment.MapViewModel
import hh.school.lesson_12_zemskov.ui.reminder_dialog_fragment.ReminderDialogViewModel

@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(ListBridgesViewModel::class)]
    fun bindListBridgesViewModel(viewModel: ListBridgesViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsBridgeViewModel::class)]
    fun bindDetailsBridgeViewModel(viewModel: DetailsBridgeViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(MapViewModel::class)]
    fun bindMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ReminderDialogViewModel::class)]
    fun bindReminderDialogViewModel(viewModel: ReminderDialogViewModel): ViewModel
}