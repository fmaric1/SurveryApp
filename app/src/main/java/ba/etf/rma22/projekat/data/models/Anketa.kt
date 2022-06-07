package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Anketa (
        @SerializedName("naziv")  var naziv: String,
        @SerializedName("nazivIstrazivanja") val nazivIstrazivanja: String,
        @SerializedName("datumPocetak")val datumPocetak: Date,
        @SerializedName("datumKraj")val datumKraj: Date? = null,
        @SerializedName("datumRada")var datumRada: Date?,
        @SerializedName("trajanje")val trajanje: Int,
        @SerializedName("nazivGrupe")val nazivGrupe: String,
        @SerializedName("progres")var progress: Float,
        @SerializedName("status")var status: statusAnkete,
        @SerializedName("anketaId")val id: Int = 0){

}