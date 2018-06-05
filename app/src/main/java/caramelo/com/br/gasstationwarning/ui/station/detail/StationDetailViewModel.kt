package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.data.repository.StationRepository
import caramelo.com.br.gasstationwarning.data.repository.StationRepositoryReceiver

class StationDetailViewModel(
        private val station: Station,
        private val lifecycle: Lifecycle,
        private val stationRepository: StationRepository
) : ViewModel() {

    private val documentId = station.getId()

    var detailLiveData: MutableLiveData<StationDetailHandler>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                doDetail()
            }
            return field
        }

    private fun doDetail() {
        detailLiveData?.postValue(StationDetailHandler.Loading())
        stationRepository.detail(documentId)
            .observe({lifecycle}) { receiver ->
                when(receiver) {
                    is StationRepositoryReceiver.Detail -> {
                        val station = receiver.station
                        val handler = StationDetailHandler.Detail(station)
                        detailLiveData?.postValue(handler)
                    }
                    is StationRepositoryReceiver.Exception -> {
                        val handler = StationDetailHandler.Detail(station)
                        detailLiveData?.postValue(handler)
                    }
                }
            }
    }
}

sealed class StationDetailHandler {
    data class Detail(val station: Station) : StationDetailHandler()
    class Loading : StationDetailHandler()
}