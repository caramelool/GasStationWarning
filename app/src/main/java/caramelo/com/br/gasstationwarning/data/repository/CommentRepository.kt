package caramelo.com.br.gasstationwarning.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import caramelo.com.br.gasstationwarning.data.model.Comment
import com.google.firebase.firestore.CollectionReference

class CommentRepository(
    private val commentCollection: CollectionReference
) {

    fun list(stationId: String): LiveData<List<Comment>> {
        val liveData = MutableLiveData<List<Comment>>()
        commentCollection
                .whereEqualTo("stationId", stationId)
                .addSnapshotListener { querySnapshot, _ ->
                    val list = querySnapshot
                            ?.map { document -> Comment.fromDocument(document) }
                            ?.sortedByDescending { it.timestamp }
                            ?: listOf()
                    liveData.postValue(list)
                }
        return liveData
    }

    fun add(comment: Comment): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        commentCollection
                .add(comment)
                .addOnCompleteListener { task ->
                    liveData.postValue(task.isSuccessful)
                }
        return liveData
    }

    fun delete(comment: Comment): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        commentCollection
                .document(comment.getId())
                .delete()
                .addOnCompleteListener { task ->
                    liveData.postValue(task.isSuccessful)
                }
        return liveData
    }

}