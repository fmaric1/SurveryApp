package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AppDatabase
import ba.etf.rma22.projekat.data.models.statusAnkete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class AnketaRepository {

    companion object {
        var mojeAnkete = ArrayList<Anketa>()
        var sveAnkete = ArrayList<Anketa>()
        var context : Context? = null
        init {

        }
        fun setCont(_context: Context?){
            context = _context
        }

        fun dodajMojeAnkete(grupa: String){
            val grupe = ArrayList<String>()
            for(x in mojeAnkete){
                if(!grupe.contains(x.nazivGrupe))
                    grupe.add(x.nazivGrupe)
            }
            for(x in sveAnkete){
                if (x.nazivGrupe == grupa && !grupe.contains(x.nazivGrupe))
                    mojeAnkete.add(x)
            }

        }
        fun getMyAnkete(): List<Anketa>{
            return mojeAnkete
        }

        fun getAllFromRepository(): List<Anketa> {
            return mojeAnkete + sveAnkete
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
            izbaciMojeAnkete()

        }
        fun izbaciMojeAnkete(){
            val nazivAnketa: ArrayList<String> = ArrayList()
            for (x in mojeAnkete){
                nazivAnketa.add(x.naziv)
            }
            val filtriraneAnkete: ArrayList<Anketa> = ArrayList()
            for(x in sveAnkete){
                if(!nazivAnketa.contains(x.naziv))
                    filtriraneAnkete.add(x)
            }
            sveAnkete =filtriraneAnkete
        }
        fun updateProgressAnkete(imeAnkete: String, nazivIstrazivanja: String){
            val pitanja = PitanjeAnketaRepository.getPitanja(imeAnkete, nazivIstrazivanja)
            var brojac: Int = 0
            for(x in pitanja){
                if(PitanjeAnketaRepository.dajOdgovor(x.naziv) != 0)
                    brojac++
            }

            for(x in mojeAnkete){
                if(x.naziv == imeAnkete && pitanja.isNotEmpty()){
                    x.progress=(brojac.toFloat()/pitanja.size)
                }
            }
        }
        fun updateStatusAnkete(imeAnkete: String){
            for(x in mojeAnkete){
                if(x.naziv == imeAnkete){
                    x.status = statusAnkete.AKTIVAN_URADEN
                }
            }
        }
        fun updateDatumRada(naziv: String){
            for(x in mojeAnkete)
                if(x.naziv == naziv) {
                    val date = Date()
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    x.datumRada = calendar.time
                }
        }
        fun dajProgress(naziv: String): Float {
            for(x in mojeAnkete)
                if(x.naziv == naziv)
                    return x.progress
            return (0).toFloat()
        }
        fun dajStatusAnkete(naziv: String): statusAnkete{
            for(x in mojeAnkete){
                if(x.naziv == naziv)
                    return x.status
            }
            return statusAnkete.AKTIVAN_URADEN
        }

        suspend fun getAll(offset:Int = 0): List<Anketa>{
            try {

                return withContext(Dispatchers.IO) {
                    val ankete = ArrayList<Anketa>()
                    if (offset == 0) {
                        for (i in 1..5) {
                            val url1 = ApiConfig.baseURL + "/anketa?offset=$i"
                            val url = URL(url1)
                            (url.openConnection() as? HttpURLConnection)?.run {
                                val result = this.inputStream.bufferedReader().use { it.readText() }
                                val items = JSONArray(result)
                                for (j in 0 until items.length()) {
                                    val anketaData = items.getJSONObject(j)
                                    ankete.add(dajNovuAnketu(anketaData))
                                }

                            }
                        }

                    } else {
                        val url1 = ApiConfig.baseURL + "/anketa?offset=$offset"
                        val url = URL(url1)
                        (url.openConnection() as? HttpURLConnection)?.run {
                            val result = this.inputStream.bufferedReader().use { it.readText() }
                            val items = JSONArray(result)
                            for (j in 0 until items.length()) {
                                val anketaData = items.getJSONObject(j)
                                ankete.add(dajNovuAnketu(anketaData))
                            }
                        }
                    }
                    sveAnkete = ankete
                    var db = AppDatabase.getInstance(context!!)
                    for(x in sveAnkete){
                        db.anketaDao().insert(x)
                    }
                    return@withContext ankete

                }
            }catch (e: JSONException){
                throw JSONException(e.message)
            }

        }
        suspend fun getByID( id: Int): Anketa? {
            return withContext(Dispatchers.IO){
                val url1 = ApiConfig.baseURL + "/anketa/$id"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run{
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val anketaData = JSONObject(result)
                    return@withContext dajNovuAnketu(anketaData)
                }
            return@withContext null
            }
        }

        suspend fun getUpisane():List<Anketa> {
            try {
                return withContext(Dispatchers.IO) {
                    val ankete = ArrayList<Anketa>()
                    val grupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
                    for (x in grupe) {
                        val id = x.id
                        val url1 = ApiConfig.baseURL + "/grupa/$id/ankete"
                        val url = URL(url1)
                        (url.openConnection() as? HttpURLConnection)?.run {
                            val result = this.inputStream.bufferedReader().use { it.readText() }
                            val items = JSONArray(result)
                            for (j in 0 until items.length()) {
                                val anketaData = items.getJSONObject(j)
                                ankete.add(dajNovuAnketu(anketaData))
                            }

                        }
                    }
                    return@withContext ankete
                }
            }
            catch (e: JSONException){
                throw IllegalArgumentException(e.message)
            }
        }


        fun dajNovuAnketu(anketaData: JSONObject): Anketa {
            try {
                var datumString = anketaData.getString("datumPocetak")
                val datumPocetak = Date(
                    datumString.subSequence(0, 4).toString().toInt() - 1900,
                    datumString.subSequence(5, 7).toString().toInt() - 1,
                    datumString.subSequence(8, 10).toString().toInt()
                )
                datumString = anketaData.get("datumKraj").toString()
                var datumKraj = Date(122,8,1)
                if (datumString != "null")
                    datumKraj = Date(
                        datumString.subSequence(0, 4).toString().toInt() - 1900,
                        datumString.subSequence(5, 7).toString().toInt() - 1,
                        datumString.subSequence(8, 10).toString().toInt()
                    )
                val anketa = Anketa(
                    anketaData.getString("naziv"),
                    "",
                    datumPocetak,
                    datumKraj,
                    null,
                    anketaData.getInt("trajanje"),
                    "",
                    0.0F,
                    statusAnkete.AKTIVAN_NIJE_URADEN,
                    anketaData.getInt("id")
                )
                return anketa


            }catch (e: JSONException){
                throw JSONException(e.message)
            }
        }

        suspend fun popuniGrupeiIstrazivanje() {
            val noveAnkete = ArrayList<Anketa>()
            return withContext(Dispatchers.IO) {
                for (x in IstrazivanjeIGrupaRepository.grupeRep) {
                    val url1 = ApiConfig.baseURL + "/grupa/${x.id}/ankete"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run{
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for(i in 0 until items.length()){
                            val anketaData = items.getJSONObject(i).getJSONObject("AnketaiGrupe")
                            val anketa = sveAnkete.first { it.id == anketaData.getInt("AnketumId") }
                            noveAnkete.add(Anketa(
                                anketa.naziv,
                                x.nazivIstrazivanja,
                                anketa.datumPocetak,
                                anketa.datumKraj,
                                anketa.datumRada,
                                anketa.trajanje,
                                x.naziv,
                                anketa.progress,
                                anketa.status,
                                anketa.id
                            ))

                        }
                    }

                }
                sveAnkete = noveAnkete
            }

        }
    }


}