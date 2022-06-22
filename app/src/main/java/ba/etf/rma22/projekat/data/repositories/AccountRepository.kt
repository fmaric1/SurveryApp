package ba.etf.rma22.projekat.data.repositories

import android.content.Context
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
        var activity: Context? = null
        var acHash : String = BuildConfig.HASH_KEY
        fun postaviHash(acHash: String):Boolean {
            this.acHash = acHash
            return acHash.isNotEmpty()
        }
        fun getHash(): String{
            return acHash
        }

        fun setContext(_activity: Context?) {
            activity = _activity
        }


    }


}