package ba.etf.rma22.projekat.data.models

import androidx.room.*

@Dao
interface IstrazivanjeDAO {
    @Query("SELECT * FROM istrazivanje")
    fun getAll(): List<Istrazivanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg istrazivanje: Istrazivanje)

    @Query("DELETE FROM istrazivanje")
    suspend fun deleteAll()
}