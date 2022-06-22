package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.models.AppDatabase
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.staticdata.getPitanjaData
import ba.etf.rma22.projekat.data.staticdata.getPitanjeAnketaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class PitanjeAnketaRepository {
    companion object{
        val mojaPitanjaAnkete: ArrayList<PitanjeAnketa> = ArrayList()
        var svaPitanja= ArrayList<Pitanje>()
        var context : Context? = null
        fun setCont(_context: Context?) {
            context = _context
        }
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

        suspend fun getPitanja(idAnkete:Int):List<Pitanje> {
            return withContext(Dispatchers.IO) {
                    val pitanja = ArrayList<Pitanje>()
                    val url1 = ApiConfig.baseURL + "/anketa/$idAnkete/pitanja"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for (i in 0 until items.length()) {
                            val pitanjeData = items.getJSONObject(i)
                            val opcijeJSON = pitanjeData.getJSONArray("opcije")
                            val opcije = ArrayList<String>()
                            for (j in 0 until opcijeJSON.length()) {
                                opcije.add(opcijeJSON.get(j).toString())
                            }
                            val pitanje = Pitanje(
                                pitanjeData.getString("naziv"),
                                pitanjeData.getString("tekstPitanja"),
                                0,
                                pitanjeData.getInt("id"),
                                pitanjeData.getJSONObject("PitanjeAnketa").getInt("AnketumId")
                            )
                            pitanja.add(pitanje)
                        }
                    }
                var db = AppDatabase.getInstance(AnketaRepository.context!!)
                for(x in pitanja){
                    db.pitanjeDao().insert(x)
                }
                    return@withContext pitanja


            }
        }

        suspend fun dajSvaPitanja() {
            val pitanja = ArrayList<Pitanje>()
            for(x in AnketaRepository.sveAnkete){
                pitanja.addAll(getPitanja(x.id))
            }
            svaPitanja = pitanja
            svaPitanja.distinct()
        }
    }
}