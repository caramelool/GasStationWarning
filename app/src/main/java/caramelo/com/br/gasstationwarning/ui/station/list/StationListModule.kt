package caramelo.com.br.gasstationwarning.ui.station.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import caramelo.com.br.gasstationwarning.ui.viewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val stationListModule = Kodein.Module {
    bind<StationListViewModel>() with provider {
        ViewModelProviders
                .of(instance<Fragment>(), instance())
                .get(StationListViewModel::class.java)
    }
    bind<ViewModelProvider.Factory>() with provider {
        viewModelFactory {
            StationListViewModel(instance(), instance())
        }
    }
}
