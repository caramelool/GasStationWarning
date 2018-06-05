package caramelo.com.br.gasstationwarning.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoArgBindingKodein
import org.kodein.di.bindings.Provider
import org.kodein.di.generic
import org.kodein.di.generic.instance
import kotlin.reflect.KClass

inline fun <reified C, reified VM: ViewModel> Kodein.BindBuilder.WithContext<C>.viewModelActivity(
        crossinline viewModel: NoArgBindingKodein<C>.() -> ViewModel?
) = Provider(contextType, generic(), {
    var factory: ViewModelProvider.Factory? = null
    viewModel()?.let { viewModel ->
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModel as T
            }
        }
    }
    ViewModelProviders
            .of(instance<FragmentActivity>(), factory)
            .get(VM::class.java)
})

inline fun <reified C, reified VM: ViewModel> Kodein.BindBuilder.WithContext<C>.viewModelFragment(
        crossinline viewModel: NoArgBindingKodein<C>.() -> ViewModel?
) = Provider(contextType, generic(), {
    var factory: ViewModelProvider.Factory? = null
    viewModel()?.let { viewModel ->
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModel as T
            }
        }
    }
    ViewModelProviders
            .of(instance<Fragment>(), factory)
            .get(VM::class.java)
})