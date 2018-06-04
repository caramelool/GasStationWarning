package caramelo.com.br.gasstationwarning.data.model

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station(
        @SerializedName("name") val name: String? = "",
        @SerializedName("address") val address: String? = "",
        @SerializedName("phone") val phone: String? = null,
        private var id: String = ""
): Parcelable {

    fun getId() = id

    companion object {
        fun fromDocument(
                document: QueryDocumentSnapshot
        ) = document.toObject(Station::class.java).apply {
            id = document.id
        }
    }

}