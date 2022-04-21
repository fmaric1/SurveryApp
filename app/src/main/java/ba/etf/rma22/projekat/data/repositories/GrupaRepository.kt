package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.getGrupe

class GrupaRepository {
    companion object {
        val grupe = ArrayList<Grupa>()
        init {
            grupe.addAll(getGrupe().filter { it.naziv == "RMA1" })
        }

        fun getGroupsByIstrazivanja(nazivIstrazivanja: String): List<Grupa> {
            return getGrupe().filter{it.nazivIstrazivanja.equals(nazivIstrazivanja)}
        }

        fun getMyGroups() : ArrayList<Grupa>{
            return grupe;
        }

        fun upisiStudenta(grupa: Grupa) {
            grupe.add(grupa)
            grupe.distinct()
        }
    }
}