package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete

class AnketaRepository {

    companion object {
        val grupe = ArrayList<String>()
        init {
            grupe.add("RMA1")
        }

        fun getMyAnkete(): List<Anketa>{
            val Anketaovi = getAnkete()
            val filtriraniAnketa: ArrayList<Anketa> = ArrayList()
            for(x in grupe){
                filtriraniAnketa.addAll(Anketaovi.filter { it.nazivGrupe.equals(x) })
            }
            return filtriraniAnketa.distinct().toList()
        }

        fun getAll(): List<Anketa> {
            return getAnkete()
        }

        fun getDone(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.AKTIVAN_URADEN) }
        }

        fun getFuture(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.NEAKTIVAN) }
        }

        fun getNotTaken(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.PROSAO) }
        }

        fun upisiStudenta(grupa: Grupa) {
            grupe.add(grupa.naziv)
            grupe.distinct()
        }

    }
}