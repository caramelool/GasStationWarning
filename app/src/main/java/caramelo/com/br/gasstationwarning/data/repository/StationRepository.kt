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
    fun list() : LiveData<StationListReceiver> {
        val liveData = MutableLiveData<StationListReceiver>()
        stationCollection
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshot, exception ->
                    if (exception != null) {
                        val receiver = StationListReceiver.Exception(exception)
                        liveData.postValue(receiver)
                        return@addSnapshotListener
                    }
                    val list = mutableListOf<Station>()
                    querySnapshot
                            ?.map { document -> Station.fromDocument(document) }
                            ?.forEach { station -> list.add(station) }
                    val receiver = StationListReceiver.List(list)
                    liveData.postValue(receiver)
                }
        return liveData
    }

    fun add(station: Station) {
        stationCollection.add(station)
                .addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> {}
                    }
                }
    }
}

sealed class StationListReceiver {
    data class List(val list: kotlin.collections.List<Station>) : StationListReceiver()
    data class Exception(val e: FirebaseFirestoreException) : StationListReceiver()
}