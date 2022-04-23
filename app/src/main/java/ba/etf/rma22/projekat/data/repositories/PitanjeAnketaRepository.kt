package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.staticdata.getPitanjaData
import ba.etf.rma22.projekat.data.staticdata.getPitanjeAnketaData

class PitanjeAnketaRepository {
    companion object{
        val mojaPitanjaAnkete: ArrayList<PitanjeAnketa> = ArrayList()

        init {
            mojaPitanjaAnkete.addAll(getPitanjeAnketaData().filter { it.naziv == "RMA_P" })
        }

        fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
            val pitanjaAnkete = getPitanjeAnketaData()
            val pitanja = getPitanjaData()
            val filtriranaPitanjaAnkete = ArrayList<String>()
            val filtriranaPitanja = ArrayList<Pitanje>()
            for(x in pitanjaAnkete){
                if(nazivAnkete == x.anketa) {
                    filtriranaPitanjaAnkete.add(x.naziv)
                    if(!mojaPitanjaAnkete.contains(x))
                        mojaPitanjaAnkete.add(x)
                }
            }
            for(x in pitanja){
                if(filtriranaPitanjaAnkete.contains(x.naziv))
                    filtriranaPitanja.add(x)
            }
            return filtriranaPitanja
        }

        fun updateOdgovor(pitanje: String, odgovor: Int){
            val nazivPitanja: ArrayList<String> = ArrayList()
            for(x in mojaPitanjaAnkete)
                nazivPitanja.add(x.naziv)
            if(!nazivPitanja.contains(pitanje))
                mojaPitanjaAnkete.addAll((getPitanjeAnketaData().filter { it.naziv == pitanje }))
            for(x in mojaPitanjaAnkete)
                if(x.naziv == pitanje)
                    x.odgovor = odgovor
        }

        fun dajOdgovor(pitanje: String) : Int {
            for(x in mojaPitanjaAnkete)
                if (x.naziv == pitanje)
                    return x.odgovor

            return 0
        }

    }
}