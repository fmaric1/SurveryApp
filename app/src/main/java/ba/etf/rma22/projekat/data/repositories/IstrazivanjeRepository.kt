package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.staticdata.getIstrazivanja

class IstrazivanjeRepository {
    companion object {
        val istrazivanja = ArrayList<String>()

        init {
            istrazivanja.add("RMA")
        }

        fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje>{
            return getIstrazivanja().filter { it.godina == godina }
        }



        fun getAll(): List<Istrazivanje> {
            return getIstrazivanja()
        }

        fun getUpisani(): List<Istrazivanje> {
            val ankete = getIstrazivanja()
            val filtriranaIstrazivanja: ArrayList<Istrazivanje> = ArrayList()
            for(x in istrazivanja){
                filtriranaIstrazivanja.addAll(ankete.filter { it.naziv.equals(x) })
            }
            return filtriranaIstrazivanja.toList()
        }

        fun upisiIstrazivanje(Istrazivanje: Istrazivanje) {
            istrazivanja.add(Istrazivanje.naziv)
            istrazivanja.distinct()
        }
    }

}