package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Istrazivanje(
    val naziv: String,
    val godina: Int,
    @PrimaryKey val id: Int = 0) {
}
