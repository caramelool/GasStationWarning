package caramelo.com.br.gasstationwarning.ui.station.comment

import android.support.v4.app.Fragment
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.getParcelable
import caramelo.com.br.gasstationwarning.ui.station.detail.EXTRA_STATION
import caramelo.com.br.gasstationwarning.ui.viewModelFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val stationCommentModule = Kodein.Module {
    bind<Station>() with provider {
        instance<Fragment>().getParcelable(EXTRA_STATION) as? Station ?: throw NullPointerException()
    }
    bind<StationCommentViewModel>() with viewModelFragment {
        StationCommentViewModel(instance(), instance(), instance(), instance())
    }
}