package caramelo.com.br.gasstationwarning.ui.about

import caramelo.com.br.gasstationwarning.ui.viewModelFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

val aboutModule = Kodein.Module {
    bind<AboutViewModel>() with viewModelFragment {
        AboutViewModel(instance())
    }
}