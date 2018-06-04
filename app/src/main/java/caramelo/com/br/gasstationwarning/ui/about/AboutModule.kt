package caramelo.com.br.gasstationwarning.ui.about

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val aboutModule = Kodein.Module {
    bind<AboutViewModel>() with provider {
        ViewModelProviders
                .of(instance<Fragment>())
                .get(AboutViewModel::class.java)
    }
}