package ba.etf.rma22.projekat.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnketaDAO {
    @Query("SELECT * FROM anketa")
    suspend fun getAll(): List<Anketa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg ankete: Anketa)

}