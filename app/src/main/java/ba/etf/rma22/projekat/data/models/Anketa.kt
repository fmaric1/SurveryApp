package ba.etf.rma22.projekat.data.models

import java.util.*

data class Anketa (
        var naziv: String, val nazivIstrazivanja: String, val datumPocetak: Date, val datumKraj: Date? = null,
        var datumRada: Date?, val trajanje: Int, val nazivGrupe: String, var progress: Float,
        var status: statusAnkete, val id: Int = 0){

}