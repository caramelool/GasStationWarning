package caramelo.com.br.gasstationwarning.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
) : Parcelable