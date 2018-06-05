package caramelo.com.br.gasstationwarning.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import caramelo.com.br.gasstationwarning.ui.viewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val loginModule = Kodein.Module {
    bind<LoginViewModel>() with provider {
        ViewModelProviders
                .of(instance<FragmentActivity>(), instance())
                .get(LoginViewModel::class.java)
    }
    bind<ViewModelProvider.Factory>() with provider {
        viewModelFactory {
            LoginViewModel(instance())
        }
    }
}