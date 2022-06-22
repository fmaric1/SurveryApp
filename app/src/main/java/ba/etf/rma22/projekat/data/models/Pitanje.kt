package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pitanje(
    val naziv: String,
    val tekstPitanja: String,
    val opcije: Int = 0,
    @PrimaryKey() val id: Int = 0,
    val idAnkete: Int = 0) {
}