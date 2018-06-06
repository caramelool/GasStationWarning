package caramelo.com.br.gasstationwarning.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fuel(
    @SerializedName("") val name: String = "",
    @SerializedName("") val price: String = ""
) : Parcelable