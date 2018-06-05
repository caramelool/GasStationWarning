package caramelo.com.br.gasstationwarning.ui.station.detail

import android.support.v4.app.FragmentActivity
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.getParcelableExtra
import caramelo.com.br.gasstationwarning.ui.viewModelActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val stationDetailModule = Kodein.Module {
    bind<Station>() with provider {
        instance<FragmentActivity>().getParcelableExtra(EXTRA_STATION) as Station
    }
    bind<StationDetailViewModel>() with viewModelActivity {
        StationDetailViewModel(instance(), instance(), instance())
    }
}