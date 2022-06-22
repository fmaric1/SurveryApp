package ba.etf.rma22.projekat.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GrupaDAO {
    @Query("SELECT * FROM grupa")
    fun getAll(): List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg grupa: Grupa)
}