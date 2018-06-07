package caramelo.com.br.gasstationwarning.ui.station.comment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import caramelo.com.br.gasstationwarning.data.UserManager
import caramelo.com.br.gasstationwarning.data.model.Comment
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.data.repository.CommentRepository

class StationCommentViewModel(
        private val station: Station,
        private val lifecycle: Lifecycle,
        private val repository: CommentRepository,
        private val userManager: UserManager
) : ViewModel() {

    val commentLiveData = MutableLiveData<List<Comment>>()

    val deleteLiveData = MutableLiveData<Comment>()

    fun listComments() {
        repository.list(station.getId())
                .observe({lifecycle}) {
                    commentLiveData.postValue(it)
                }
    }

    fun addComment(text: String) {
        if (text.isBlank()) return
        val comment = Comment(
                userManager.getUser(),
                station.getId(),
                text)
        repository.add(comment)
    }

    fun checkDeleteComment(comment: Comment) {
        if (comment.user?.id == userManager.getUser().id) {
            deleteLiveData.value = comment
        }
        deleteLiveData.value = null
    }

    fun deleteComment(comment: Comment) {
        repository.delete(comment)
    }
}