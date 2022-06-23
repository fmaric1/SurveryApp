package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AppDatabase
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class IstrazivanjeIGrupaRepository {
    companion object {
        var istrazivanjaRep = ArrayList<Istrazivanje>()
        var grupeRep = ArrayList<Grupa>()
        var mojeGrupe = ArrayList<Grupa>()
        var mojaIstrazivanja = ArrayList<Istrazivanje>()
        var context : Context? = null


        suspend fun grupeSaIstrazivanjima(){
            return withContext(Dispatchers.IO){
                for(x in grupeRep){
                   val url1 = ApiConfig.baseURL + "/grupa/${x.id}/istrazivanje"
                    val url  = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run{
                        val result = this.inputStream.bufferedReader().use{ it.readText()}
                        val istrazivanjeData = JSONObject(result)
                        x.nazivIstrazivanja = istrazivanjeData.getString("naziv")
                        x.idIstrazivanja = istrazivanjeData.getInt("id")
                    }

                }
                for(x in mojeGrupe){
                    val url1 = ApiConfig.baseURL + "/grupa/${x.id}/istrazivanje"
                    val url  = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run{
                        val result = this.inputStream.bufferedReader().use{ it.readText()}
                        val istrazivanjeData = JSONObject(result)
                        x.nazivIstrazivanja = istrazivanjeData.getString("naziv")
                        x.idIstrazivanja = istrazivanjeData.getInt("id")
                    }
                }
            }
        }
        suspend fun getIstrazivanja(offset: Int = 0): List<Istrazivanje> {
            val db = AppDatabase.getInstance(context!!)
            db.istrazivanjeDao().deleteAll()
            return withContext(Dispatchers.IO) {
                val istrazivanja = ArrayList<Istrazivanje>()
                if (offset == 0) {
                    for (i in 1 until 5) {
                        val url1 = ApiConfig.baseURL + "/istrazivanje?offset=$i"
                        val url = URL(url1)
                        (url.openConnection() as? HttpURLConnection)?.run {
                            val result = this.inputStream.bufferedReader().use { it.readText() }
                            val items = JSONArray(result)
                            for (j in 0 until items.length()) {
                                val istrazivanjeData = items.getJSONObject(j)
                                val istrazivanje =
                                    Istrazivanje(
                                        istrazivanjeData.getString("naziv"),
                                        istrazivanjeData.getInt("godina"),
                                        istrazivanjeData.getInt("id")
                                    )
                                db.istrazivanjeDao().insert(istrazivanje)
                                istrazivanja.add(istrazivanje)
                            }
                        }
                    }
                } else {
                    val url1 = ApiConfig.baseURL + "/istrazivanje?offset=$offset"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for (j in 0 until items.length()) {
                            val istrazivanjeData = items.getJSONObject(j)
                            val istrazivanje =
                                Istrazivanje(
                                    istrazivanjeData.getString("naziv"),
                                    istrazivanjeData.getInt("godina"),
                                    istrazivanjeData.getInt("id")
                                )
                            db.istrazivanjeDao().insert(istrazivanje)
                            istrazivanja.add(istrazivanje)
                        }
                    }
                }
                istrazivanjaRep = istrazivanja



                return@withContext istrazivanja

            }
        }

        suspend fun getGrupe(): List<Grupa> {
            val grupe = ArrayList<Grupa>()
            return withContext(Dispatchers.IO) {
                val url1 = ApiConfig.baseURL + "/grupa/"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val grupa = Grupa(
                            items.getJSONObject(i).getString("naziv"),
                            "",
                            items.getJSONObject(i).getInt("id")
                        )
                        grupe.add(grupa)
                    }
                }
                grupeRep = grupe
                val db = AppDatabase.getInstance(context!!)
                for(x in grupe){
                    db.grupaDao().insert(x)
                }
                return@withContext grupe
            }
        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
            val grupe = ArrayList<Grupa>()
            return withContext(Dispatchers.IO) {
                val url1 = ApiConfig.baseURL + "/grupa/"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        if (items.getJSONObject(i).getInt("IstrazivanjeId") == idIstrazivanja) {
                            val grupa = Grupa(
                                items.getJSONObject(i).getString("naziv"),
                                "",
                                items.getJSONObject(i).getInt("id")
                            )
                            grupe.add(grupa)
                        }
                    }
                }
                return@withContext grupe
            }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            if(isNetworkAvailable(context)) {
                return withContext(Dispatchers.IO) {
                    val url1 =
                        ApiConfig.baseURL + "/grupa/$idGrupa" + "/student/" + AccountRepository.acHash
                    val url = URL(url1)
                    val con = (url.openConnection() as? HttpURLConnection)
                    if (con != null) {
                        con.requestMethod = "POST"
                    };
                    con?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val message = JSONObject(result).getString("message")
                        if (message.equals("Grupa not found."))
                            return@withContext false

                    }
                    getUpisaneGrupe()
                    grupeSaIstrazivanjima()
                    return@withContext true
                }
            }
            return false
        }

        suspend fun getUpisaneGrupe(): List<Grupa> {
            try {
                val grupe = ArrayList<Grupa>()
                return withContext(Dispatchers.IO) {
                    val url1 = ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/grupa"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for (i in 0 until items.length()) {
                            val grupa = Grupa(
                                items.getJSONObject(i).getString("naziv"),
                                "",
                                items.getJSONObject(i).getInt("id")
                            )
                            AnketaRepository.dodajMojeAnkete(grupa.naziv)
                            grupe.add(grupa)
                        }
                    }
                    mojeGrupe = grupe
                    grupeSaIstrazivanjima()
                    for(x in grupe){
                        for(y in istrazivanjaRep)
                            if(y.id == x.idIstrazivanja)
                                mojaIstrazivanja.add(y)

                    }

                    mojaIstrazivanja.distinct()
                    return@withContext grupe
                }
            }
            catch (e: JSONException){
                throw JSONException(e.message)
            }
        }

        fun getIstrazivanjeByGodina(godina: Int): List<Istrazivanje> {
            return istrazivanjaRep.filter { it.godina == godina }
        }

        fun getGroupsByIstrazivanja(istrazivanje: String): List<Grupa> {
            return grupeRep.filter { it.nazivIstrazivanja == istrazivanje }
        }

        fun getUpisanaIstrazivanja(): List<Istrazivanje> {
            return mojaIstrazivanja
        }

        fun setCont(_context: Context?) {
            context = _context
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