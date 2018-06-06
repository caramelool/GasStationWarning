package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.data.repository.StationRepository
import caramelo.com.br.gasstationwarning.data.repository.StationRepositoryReceiver

class StationDetailViewModel(
        val station: Station,
        private val lifecycle: Lifecycle,
        private val repository: StationRepository
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
        repository.detail(documentId)
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

    val shareBody by lazy {
        val station = station
        return@lazy StringBuffer().apply {
            append("*${station.name}*")
            station.fuels?.forEach { fuel ->
                append("\n")
                append("*${fuel.name}:* ${fuel.price}")
            }
            append("\n")
            append(station.address)
            if (station.phone.isNullOrBlank().not()) {
                append("\n")
                append(station.phone)
            }
            if (station.link.isNullOrBlank().not()) {
                append("\n")
                append(station.link)
            }
        }.toString()
    }
}

sealed class StationDetailHandler {
    data class Detail(val station: Station) : StationDetailHandler()
    class Loading : StationDetailHandler()
}