package caramelo.com.br.gasstationwarning.ui.login

import caramelo.com.br.gasstationwarning.ui.viewModelActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

val loginModule = Kodein.Module {
    bind<LoginViewModel>() with viewModelActivity {
        LoginViewModel(instance())
    }
}