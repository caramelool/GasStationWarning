package caramelo.com.br.gasstationwarning.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
fun viewModelFactory(
        viewModel: () -> ViewModel?
) = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel() as T
    }
}