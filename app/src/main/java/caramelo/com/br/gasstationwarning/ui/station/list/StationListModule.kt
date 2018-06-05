package caramelo.com.br.gasstationwarning.ui.station.list

import caramelo.com.br.gasstationwarning.ui.viewModelFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

val stationListModule = Kodein.Module {
    bind<StationListViewModel>() with viewModelFragment {
        StationListViewModel(instance(), instance())
    }
}
