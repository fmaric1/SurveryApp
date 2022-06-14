package ba.etf.rma22.projekat.data.models

import androidx.room.PrimaryKey

data class Istrazivanje(
    val naziv: String,
    val godina: Int,
    @PrimaryKey val id: Int = 0) {
}
