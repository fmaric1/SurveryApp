package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.Istrazivanje

fun getIstrazivanja(): List<Istrazivanje>{
    return listOf(
            Istrazivanje("RPR", 2),
            Istrazivanje("RMA", 2),
            Istrazivanje("TP", 1),
            Istrazivanje("VI", 3)
    )
}