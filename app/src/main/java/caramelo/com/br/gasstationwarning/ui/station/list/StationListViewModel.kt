package caramelo.com.br.gasstationwarning.ui.station.list

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.data.repository.StationRepository
import caramelo.com.br.gasstationwarning.data.repository.StationRepositoryReceiver

class StationListViewModel(
        private val lifecycle: Lifecycle,
        private val repository: StationRepository
) : ViewModel() {

    var stationLiveData: MutableLiveData<StationListHandle>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                doList()
            }
            return field
        }

    private fun doList() {
        showLoading()
        repository.list().observe({lifecycle}) { receiver ->
            val handler = when(receiver) {
                is StationRepositoryReceiver.List -> {
                    if (receiver.list.isNotEmpty()) {
                        StationListHandle.Receiver(receiver.list)
                    } else {
                        StationListHandle.Empty()
                    }
                }
                else -> StationListHandle.Empty()
            }
            stationLiveData?.postValue(handler)

        }
    }

    private fun showLoading() {
        stationLiveData?.postValue(StationListHandle.Loading())
    }
}

sealed class StationListHandle {
    data class Receiver(
            val list: List<Station>
    ) : StationListHandle()

    class Empty : StationListHandle()

    class Loading : StationListHandle()
}
