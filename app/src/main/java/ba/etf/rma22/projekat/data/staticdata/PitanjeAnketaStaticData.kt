package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.PitanjeAnketa

fun getPitanjeAnketaData(): List<PitanjeAnketa>{
    return listOf(
            PitanjeAnketa("RMA_P", "Anketa 1",3),
            PitanjeAnketa("RMA_V", "Anketa 1",0 ),
            PitanjeAnketa("RMA_O", "Anketa 1",0),

            PitanjeAnketa("RPR_P", "Anketa 2",0),
            PitanjeAnketa("RPR_V", "Anketa 2",0),
            PitanjeAnketa("RPR_O", "Anketa 2",0),

            PitanjeAnketa("RPR_P", "Anketa 3",0),
            PitanjeAnketa("RPR_V", "Anketa 3",0),
            PitanjeAnketa("RPR_O", "Anketa 3",0),

            PitanjeAnketa("VI_P", "Anketa 6",0),
            PitanjeAnketa("VI_V", "Anketa 6",0),
            PitanjeAnketa("VI_O", "Anketa 6",0),

            PitanjeAnketa("VI_P", "Anketa 7",0),
            PitanjeAnketa("VI_V", "Anketa 7",0),
            PitanjeAnketa("VI_O", "Anketa 7",0),

            PitanjeAnketa("TP_P", "Anketa 4",0),
            PitanjeAnketa("TP_V", "Anketa 4",0),
            PitanjeAnketa("TP_O", "Anketa 4",0),

            PitanjeAnketa("TP_P", "Anketa 5",0),
            PitanjeAnketa("TP_V", "Anketa 5",0),
            PitanjeAnketa("TP_O", "Anketa 5",0)


        )
}