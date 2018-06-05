package caramelo.com.br.gasstationwarning.ui.splash

import caramelo.com.br.gasstationwarning.ui.viewModelActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

val splashModule = Kodein.Module {
    bind<SplashViewModel>() with viewModelActivity {
        SplashViewModel(instance())
    }
}