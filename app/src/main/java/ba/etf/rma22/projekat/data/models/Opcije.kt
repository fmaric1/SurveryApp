package ba.etf.rma22.projekat.data.models

import androidx.room.Entity

@Entity(primaryKeys = ["idPitanja", "idOpcije"])
data class Opcije(
    val idPitanja: Long,
    val idOpcije: Long
) {

}
