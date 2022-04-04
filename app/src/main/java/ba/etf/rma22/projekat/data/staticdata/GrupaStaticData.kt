package ba.etf.rma22.projekat.data.staticdata


import ba.etf.rma22.projekat.data.models.Grupa

fun getGrupe(): List<Grupa> {
    return listOf(
            Grupa("RMA1", "RMA"),
            Grupa("TP1", "TP"),
            Grupa("TP2", "TP"),
            Grupa("RPR1", "RPR"),
            Grupa("RPR2", "RPR"),
            Grupa("VI1", "VI"),
            Grupa("VI2", "VI")
    )
}