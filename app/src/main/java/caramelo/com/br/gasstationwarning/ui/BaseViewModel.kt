package caramelo.com.br.gasstationwarning.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoArgBindingKodein
import org.kodein.di.bindings.Provider
import org.kodein.di.generic

fun <C> Kodein.BindBuilder.WithContext<C>.viewModelFactory(
        creator: NoArgBindingKodein<C>.() -> ViewModel
) = Provider<C, ViewModelProvider.Factory>(contextType, generic(), {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return creator() as T
        }
    }
})