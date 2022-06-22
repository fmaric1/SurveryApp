package ba.etf.rma22.projekat.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PitanjeDAO {
    @Query("SELECT * FROM pitanje")
    fun getAll(): List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg pitanje: Pitanje)
}
