package ba.etf.rma22.projekat.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TakeAnketaDAO {
    @Query("SELECT * FROM anketaTaken")
    fun getAll(): List<AnketaTaken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg anketataken: AnketaTaken)
}