package hh.school.lesson_12_zemskov.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerFragment
import hh.school.lesson_12_zemskov.di.ViewModelFactory
import javax.inject.Inject

/**
 * Базовый класс для всех фрагментов приложения.
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : DaggerFragment(contentLayoutId) {
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    /**
     * Хранит [BroadcastReceiver], получающий сообщения каждую минуту.
     */
    private val timeTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            onTimeTickReceive()
        }
    }

    protected inline fun <reified VM : ViewModel> daggerViewModels() =
        viewModels<VM> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().registerReceiver(timeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onDestroyView() {
        requireContext().unregisterReceiver(timeTickReceiver)
        super.onDestroyView()
    }

    /**
     * Вызывается при получения широковещательного сообщения от [timeTickReceiver].
     */
    abstract fun onTimeTickReceive()
}