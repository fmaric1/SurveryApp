package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.staticdata.getGrupe
import ba.etf.rma22.projekat.data.staticdata.getIstrazivanja

class IstrazivanjeRepository {
    companion object {
        val mojaIstrazivanja = ArrayList<Istrazivanje>()

        init {
            mojaIstrazivanja.addAll(getIstrazivanja().filter { it.naziv == "RMA" })
        }

        fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje>{
            return getIstrazivanja().filter { it.godina == godina }
        }



        fun getAll(): List<Istrazivanje> {
            return getIstrazivanja()
        }

        fun getUpisanaIstrazivanja(): List<Istrazivanje> {
            return mojaIstrazivanja
        }


        fun upisiIstrazivanje(Istrazivanje: Istrazivanje) {
            mojaIstrazivanja.add(Istrazivanje)
            mojaIstrazivanja.distinct()
            AnketaRepository.dodajAnketu()
        }

    }

}