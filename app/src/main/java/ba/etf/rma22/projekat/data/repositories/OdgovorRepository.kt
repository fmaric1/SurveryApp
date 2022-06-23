package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ba.etf.rma22.projekat.data.models.AppDatabase
import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import java.net.URL

class OdgovorRepository {
    companion object {
        var odgovori = ArrayList<Odgovor>()
        var neposlaniOdgovori = ArrayList<Odgovor>()
        var context: Context? = null
        fun setCont(_context: Context?) {
            context = _context
        }
        suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
            try {
                return withContext(Dispatchers.IO) {
                    val odgovori = ArrayList<Odgovor>()
                    val ankete =  TakeAnketaRepository.getPoceteAnkete()
                    var idAnketaTaken = 0
                    if (ankete != null) {
                        for(x in ankete){
                            if(x.AnketumId == idAnkete)
                                idAnketaTaken = x.id
                        }
                    }
                    val url1 =
                        ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketataken/$idAnketaTaken/odgovori"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for (i in 0 until items.length()) {
                            val odgovorData = items.getJSONObject(i)
                            val odgovor = Odgovor(
                                odgovorData.getInt("PitanjeId"),
                                odgovorData.getInt("odgovoreno")
                            )
                            odgovori.add(odgovor)

                        }
                    }
                    val db = AppDatabase.getInstance(context!!)
                    for(x in odgovori){
                        db.odgovorDao().insert(x)
                    }
                    return@withContext odgovori


                }
            }catch (e: JSONException){
                throw IllegalArgumentException(e.message)
            }
        }

        suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
            if(isNetworkAvailable(context)) {
                return withContext(Dispatchers.IO) {

                    val db = AppDatabase.getInstance(context!!)
                    db.odgovorDao().insert(Odgovor(idPitanje, odgovor, idAnketaTaken))

                    val url1 =
                        ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketataken/$idAnketaTaken/odgovor"
                    val url = URL(url1)
                    val con = (url.openConnection() as HttpURLConnection)
                    con.requestMethod = "POST";
                    con.setRequestProperty("Content-Type", "application/json")
                    con.setRequestProperty("Accept", "application/json")
                    con.doOutput = true;
                    val ankete = TakeAnketaRepository.getPoceteAnkete()
                    var idAnkete = 0
                    if (ankete != null)
                        for (x in ankete) {
                            if (idAnketaTaken == x.id)
                                idAnkete = x.AnketumId
                        }
                    val brojPitanja = PitanjeAnketaRepository.getPitanja(idAnkete).size
                    val progress =
                        ((getOdgovoriAnketa(idAnketaTaken).size.toDouble() + 1.0) / brojPitanja)
                    var progressInt = 0
                    if (progress < 0.1)
                        progressInt = 0
                    else if (progress >= 0.1 && progress < 0.3)
                        progressInt = 20
                    else if (progress >= 0.3 && progress < 0.5)
                        progressInt = 40
                    else if (progress >= 0.5 && progress < 0.7)
                        progressInt = 60
                    else if (progress >= 0.7 && progress < 1.0)
                        progressInt = 80
                    else if (progress >= 1.0)
                        progressInt = 100
                    val jsonInputString =
                        "{\"odgovor\": \"$odgovor\", \"pitanje\": \"$idPitanje\", \"progres\": \"$progress\"}"
                    con.outputStream.use { os ->
                        val input = jsonInputString.toByteArray(charset("utf-8"))
                        os.write(input, 0, input.size)
                    }
                    BufferedReader(
                        InputStreamReader(con.inputStream, "utf-8")
                    ).use { br ->
                        val response = StringBuilder()
                        var responseLine: String? = null
                        while (br.readLine().also({ responseLine = it }) != null) {
                            response.append(responseLine!!.trim { it <= ' ' })
                        }
                        println(response.toString())
                    }
                    return@withContext progressInt
                }
            }
            return 0

        }

        suspend fun dodajNeposlani(idPitanja: Int, odgovoreno: Int, idAnketaTaken: Int) {
            val pomocniNiz = ArrayList<Pair<Int,Int>>()
            val odgovor  = Odgovor(idPitanja,odgovoreno,idAnketaTaken)
            for(x in neposlaniOdgovori )
                pomocniNiz.add(Pair(x.id,x.idAnketaTaken))
            for(x in odgovori )
                pomocniNiz.add(Pair(x.id,x.idAnketaTaken))
            if(!pomocniNiz.contains(Pair(idPitanja, idAnketaTaken))) {
                neposlaniOdgovori.add(odgovor)
            }
        }
        suspend fun posaljiOdgovore(){
            for(x in neposlaniOdgovori){
                postaviOdgovorAnketa(x.idAnketaTaken,x.id,x.odgovoreno)
            }
            neposlaniOdgovori.clear()
        }
        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }
    }
}