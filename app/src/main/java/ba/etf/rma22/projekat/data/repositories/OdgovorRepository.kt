package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OdgovorRepository {
    companion object {
        suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                val odgovori = ArrayList<Odgovor>()
                val url1 =
                    ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketataken/$idAnkete/odgovori"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val odgovorData = items.getJSONObject(i)
                        val odgovor = Odgovor(
                            odgovorData.getInt("id"),
                            odgovorData.getInt("odgovoreno")
                        )
                        odgovori.add(odgovor)

                    }
                }

                return@withContext odgovori


            }
        }

        suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                val url1 = ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/anketataken/$idAnketaTaken/odgovor"
                val url = URL(url1)
                val con = (url.openConnection() as HttpURLConnection)
                con.requestMethod = "POST";
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("Accept", "application/json")
                con.doOutput = true;
                val progress = OdgovorRepository.getOdgovoriAnketa(idAnketaTaken).size
                val jsonInputString = "{\"odgovor\": \"$odgovor\", \"pitanje\": \"$idPitanje\", \"progres\": \"$progress\"}"
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
                return@withContext progress
            }

        }
    }
}