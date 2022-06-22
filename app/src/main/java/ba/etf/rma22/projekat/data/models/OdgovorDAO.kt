package ba.etf.rma22.projekat.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OdgovorDAO {
    @Query("SELECT * FROM odgovor")
    fun getAll(): List<Odgovor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg odgovor: Odgovor)

    @Query("DELETE FROM odgovor")
    suspend fun deleteAll()
}