package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.models.AppDatabase

class DBRepository {
    companion object {
        var db : AppDatabase? = null

        suspend fun updateNow() {

        }

        fun dajBazu(context: Context){
            AppDatabase.getInstance(context)
        }
    }

}
