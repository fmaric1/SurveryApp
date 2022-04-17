package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.staticdata.getPitanjaData
import ba.etf.rma22.projekat.data.staticdata.getPitanjeAnketaData

class PitanjaAnketaRepository {
    companion object{
        fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
            val pitanjaAnkete = getPitanjeAnketaData()
            val pitanja = getPitanjaData()
            val filtriranaPitanjaAnkete = ArrayList<String>()
            val filtriranaPitanja = ArrayList<Pitanje>()
            for(x in pitanjaAnkete){
                if(nazivAnkete == x.anketa)
                    filtriranaPitanjaAnkete.add(x.naziv)
            }
            for(x in pitanja){
                if(filtriranaPitanjaAnkete.contains(x.naziv))
                    filtriranaPitanja.add(x)
            }
            return filtriranaPitanja
        }
    }
}