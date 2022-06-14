package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grupa(
    val naziv: String,
    var nazivIstrazivanja: String,
    @PrimaryKey val id: Int = 0,
    var idIstrazivanja: Int = 0) {
}