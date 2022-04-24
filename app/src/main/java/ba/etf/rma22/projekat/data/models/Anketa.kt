package ba.etf.rma22.projekat.data.models

import java.util.*

data class Anketa (
        var naziv: String, val nazivIstrazivanja: String, val datumPocetka: Date, val datumKraj: Date,
        var datumRada: Date?, val trajanje: Int, val nazivGrupe: String, var progress: Float,
        var status: statusAnkete
){

}