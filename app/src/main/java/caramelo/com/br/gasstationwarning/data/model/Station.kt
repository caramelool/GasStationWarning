package caramelo.com.br.gasstationwarning.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Station(
        @SerializedName("name") val name: String? = "",
        @SerializedName("address") val address: String? = "",
        @SerializedName("phone") val phone: String? = null,
        @SerializedName("description") val description: String? = null,
        @SerializedName("hasFuel") val hasFuel: Boolean = true,
        @SerializedName("fuels") val fuels: List<Fuel>? = null,
        @SerializedName("link") val link: String? = null,
        @SerializedName("rating") val rating: Float = 0f,
        @get:Exclude private var id: String = ""
): Parcelable {

    fun getId() = id

    @IgnoredOnParcel
    @SerializedName("location") val location: GeoPoint? = null

    companion object {
        fun fromDocument(
                document: DocumentSnapshot?
        ) = document?.toObject(Station::class.java)?.apply {
            id = document.id
        } ?: throw NullPointerException()
    }

}