package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import ba.etf.rma22.projekat.data.staticdata.getGrupe
import ba.etf.rma22.projekat.data.staticdata.getIstrazivanja

class AnketaRepository {

    companion object {
        val mojeAnkete = ArrayList<Anketa>()
        init {
            val Ankete = getAnkete()
            for(x in GrupaRepository.getMyGroups()){
                mojeAnkete.addAll(Ankete.filter { it.nazivGrupe == x.naziv })
            }
        }

        fun getMyAnkete(): List<Anketa>{
            return mojeAnkete
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

        fun dodajAnketu(){
            val nazivAnketa: ArrayList<String> = ArrayList()
            for (x in mojeAnkete){
                nazivAnketa.add(x.naziv)
            }
            for(x in getAnkete()){
                for(y in GrupaRepository.getMyGroups()){
                    if(x.nazivGrupe == y.naziv && !nazivAnketa.contains(x.naziv))
                        mojeAnkete.add(x)
                }
            }
        }




    }
}