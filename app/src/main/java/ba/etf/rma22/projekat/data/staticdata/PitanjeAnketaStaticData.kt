package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.PitanjeAnketa

fun getPitanjeAnketaData(): List<PitanjeAnketa>{
    return listOf(
        PitanjeAnketa("RMA_P", "Anketa 1"),
        PitanjeAnketa("RMA_V", "Anketa 1"),
        PitanjeAnketa("RMA_O", "Anketa 1")
        )
}