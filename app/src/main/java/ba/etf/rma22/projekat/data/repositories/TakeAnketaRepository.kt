package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class TakeAnketaRepository {

    companion object {
        val poceteAnkete = ArrayList<AnketaTaken>()
        suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
            try{

            return withContext(Dispatchers.IO) {

                val url1 =
                    ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketa/$idAnkete"
                val url = URL(url1)
                val con = (url.openConnection() as HttpURLConnection)
                con.requestMethod = "POST"
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("Accept", "application/json")
                con.doOutput = true;
                val jsonInputString = ""
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

                    val anketaData = JSONObject(response.toString())

                   val datumString = anketaData.get("datumRada").toString()

                        val datumRada = Date(
                            datumString.subSequence(0, 4).toString().toInt() - 1900,
                            datumString.subSequence(5, 7).toString().toInt() - 1,
                            datumString.subSequence(8, 10).toString().toInt()
                        )
                        val anketaTaken = AnketaTaken(
                            anketaData.getInt("id"),
                            anketaData.getString("student"),
                            anketaData.getInt("progres"),
                            datumRada,
                            anketaData.getInt("AnketumId")
                        )
                    poceteAnkete.add(anketaTaken)
                    return@withContext anketaTaken
                }

            }
        }
            catch (e: JSONException){
                return null
            }
        }

        suspend fun getPoceteAnkete(): List<AnketaTaken>? {
            try {
                val ankete = ArrayList<AnketaTaken>()
                return withContext(Dispatchers.IO) {
                    val url1 =
                        ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketataken"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(result)
                        for (i in 0 until items.length()) {
                            val anketaData = items.getJSONObject(i)
                            val datumString = anketaData.get("datumRada").toString()
                            var datumRada = Date()
                            datumRada = Date(
                                datumString.subSequence(0, 4).toString().toInt() - 1900,
                                datumString.subSequence(5, 7).toString().toInt() - 1,
                                datumString.subSequence(8, 10).toString().toInt()
                            )
                            ankete.add(
                                AnketaTaken(
                                    anketaData.getInt("id"),
                                    anketaData.getString("student"),
                                    anketaData.getInt("progres"),
                                    datumRada,
                                    anketaData.getInt("AnketumId")
                                )
                            )
                        }
                        for(x in ankete){
                            if(!poceteAnkete.contains(x))
                                poceteAnkete.add(x)
                        }
                        if(ankete.size == 0)
                            return@withContext null
                        return@withContext ankete
                    }
                }
            }
            catch (e: JSONException){
                throw JSONException(e.message)
            }
        }
    }
}