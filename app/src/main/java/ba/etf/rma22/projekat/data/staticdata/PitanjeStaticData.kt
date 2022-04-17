package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.Pitanje

fun getPitanjaData(): List<Pitanje>{
    return listOf(
        Pitanje("RMA_P", "Ocijenite predavanje: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("RMA_V", "Ocijenite vjezbe: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("RMA_O", "Ukupna ocjena predmeta: ", listOf("1", "2", "3", "4", "5")),

        Pitanje("RPR_P", "Ocijenite predavanje: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("RPR_V", "Ocijenite vjezbe: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("RPR_O", "Ukupna ocjena predmeta: ", listOf("1", "2", "3", "4", "5")),

        Pitanje("TP_P", "Ocijenite predavanje: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("TP_V", "Ocijenite vjezbe: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("TP_O", "Ukupna ocjena predmeta: ", listOf("1", "2", "3", "4", "5")),

        Pitanje("VI_P", "Ocijenite predavanje: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("VI_V", "Ocijenite vjezbe: ", listOf("1", "2", "3", "4", "5")),
        Pitanje("VI_O", "Ukupna ocjena predmeta: ", listOf("1", "2", "3", "4", "5"))
    )
}