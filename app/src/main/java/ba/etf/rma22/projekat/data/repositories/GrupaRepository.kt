package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.getGrupe

class GrupaRepository {
    companion object {
        init {
            // TODO: Implementirati
        }

        fun getGroupsByIstrazivanja(nazivIstrazivanja: String): List<Grupa> {
            return getGrupe().filter{it.nazivIstrazivanja.equals(nazivIstrazivanja)}
        }
    }
}