package caramelo.com.br.gasstationwarning.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Station(
        @SerializedName("name") val name: String? = "",
        @SerializedName("address") val address: String? = "",
        @SerializedName("phone") val phone: String? = null,
        @SerializedName("description") val description: String? = null,
        @SerializedName("hasFull") val hasFull: Boolean = true,
        @SerializedName("fuels") val fuels: List<Fuel>? = null,
        @get:Exclude private var id: String = ""
): Parcelable {

    fun getId() = id

    companion object {
        fun fromDocument(
                document: DocumentSnapshot?
        ) = document?.toObject(Station::class.java)?.apply {
            id = document.id
        } ?: throw NullPointerException()
    }

}