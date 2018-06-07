package caramelo.com.br.gasstationwarning.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import caramelo.com.br.gasstationwarning.data.model.Station
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class StationRepository(
    private val stationCollection: CollectionReference
) {
    fun list() : LiveData<StationRepositoryReceiver> {
        val liveData = MutableLiveData<StationRepositoryReceiver>()
        stationCollection
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, exception ->
                val receiver = when(exception) {
                    null -> {
                        val list = querySnapshot
                                ?.map { document -> Station.fromDocument(document) }
                                ?.sortedByDescending { it.hasFuel }
                                ?: listOf()
                        StationRepositoryReceiver.List(list)
                    }
                    else -> {
                        StationRepositoryReceiver.Exception(exception)
                    }
                }
                liveData.postValue(receiver)
            }
        return liveData
    }

    fun detail(documentId: String) : LiveData<StationRepositoryReceiver> {
        val liveData = MutableLiveData<StationRepositoryReceiver>()
        stationCollection
            .document(documentId)
            .addSnapshotListener { documentSnapshot, exception ->
                val receiver = when (exception) {
                    null -> {
                        val station = Station.fromDocument(documentSnapshot)
                        StationRepositoryReceiver.Detail(station)
                    }
                    else -> {
                        StationRepositoryReceiver.Exception(exception)
                    }
                }
                liveData.postValue(receiver)
            }
        return liveData
    }
}

sealed class StationRepositoryReceiver {
    data class List(val list: kotlin.collections.List<Station>) : StationRepositoryReceiver()
    data class Detail(val station: Station) : StationRepositoryReceiver()
    data class Exception(val e: FirebaseFirestoreException) : StationRepositoryReceiver()
}