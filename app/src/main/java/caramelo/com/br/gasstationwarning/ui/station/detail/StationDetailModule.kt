package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.viewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val stationDetailModule = Kodein.Module {
    bind<StationDetailViewModel>() with provider {
        ViewModelProviders
                .of(instance<FragmentActivity>(), instance())
                .get(StationDetailViewModel::class.java)
    }
    bind<Station>() with provider {
        instance<FragmentActivity>().intent.getParcelableExtra(EXTRA_STATION) as Station
    }
    bind<ViewModelProvider.Factory>() with provider {
        viewModelFactory {
            StationDetailViewModel(instance(), instance(), instance())
        }
    }
}