package hh.school.lesson_12_zemskov

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import hh.school.lesson_12_zemskov.di.ViewModelFactory
import javax.inject.Inject

open class BaseDialogFragment : DialogFragment(), HasAndroidInjector {

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    protected inline fun <reified VM : ViewModel> daggerViewModels() =
        viewModels<VM> { viewModelFactory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}