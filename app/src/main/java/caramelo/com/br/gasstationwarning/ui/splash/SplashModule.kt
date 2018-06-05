package caramelo.com.br.gasstationwarning.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import caramelo.com.br.gasstationwarning.ui.viewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val splashModule = Kodein.Module {
    bind<SplashViewModel>() with provider {
        ViewModelProviders
                .of(instance<FragmentActivity>(), instance())
                .get(SplashViewModel::class.java)
    }
    bind<ViewModelProvider.Factory>() with provider {
        viewModelFactory {
            SplashViewModel(instance())
        }
    }
}