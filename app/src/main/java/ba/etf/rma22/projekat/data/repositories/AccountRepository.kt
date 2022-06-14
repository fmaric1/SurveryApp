package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.BuildConfig
import ba.etf.rma22.projekat.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class AccountRepository {
    companion object{
        var acHash : String = BuildConfig.HASH_KEY
        fun postaviHash(acHash: String):Boolean {
            this.acHash = acHash
            return acHash.isNotEmpty()
        }
        fun getHash(): String{
            return acHash
        }
        suspend fun dajMail(): String? {
            return withContext(Dispatchers.IO){
                val url1 = ApiConfig.baseURL + "/student/$acHash"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result  = this.inputStream.bufferedReader().use{ it.readText()}
                    return@withContext JSONObject(result).getString("account")
                }
            }

        }

        fun setContext(activity: MainActivity?) {
            this.setContext(activity)
        }
    }


}