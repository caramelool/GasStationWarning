package caramelo.com.br.gasstationwarning.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
@Parcelize
data class Comment(
        @SerializedName("user") val user: User? = null,
        @SerializedName("stationId") val stationId: String = "",
        @SerializedName("text") val text: String = "",
        @SerializedName("date") val date: String = date(),
        @SerializedName("timestamp") val timestamp: String = timestamp(),
        @get:Exclude private var id: String = ""
) : Parcelable {

    fun getId() = id

    companion object {
        fun fromDocument(
                document: DocumentSnapshot?
        ) = document?.toObject(Comment::class.java)?.apply {
            id = document.id
        } ?: throw NullPointerException()
    }

}

@SuppressLint("SimpleDateFormat")
private fun date() = Calendar.getInstance().let {
    SimpleDateFormat("dd/MM/yyyy hh:mm").format(it.time)
}

@SuppressLint("SimpleDateFormat")
private fun timestamp() = Calendar.getInstance().let {
    SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(it.time)
}