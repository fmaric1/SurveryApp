package ba.etf.rma22.projekat.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OpcijeZaPitanje(
    @Embedded val pitanje: Pitanje,
    @Relation(
        parentColumn = "idPitanja",
        entityColumn = "idOpcije",
        associateBy = Junction(value = Opcije::class,
            parentColumn = "idPitanja",
            entityColumn = "idOpcije")
    )
    val opcije :List<String>
) {
}
