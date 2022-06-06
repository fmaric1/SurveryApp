package ba.etf.rma22.projekat.data.models

data class Pitanje(val naziv: String, val tekstPitanja: String,val opcije: List<String>, val id: Int = 0) {
}