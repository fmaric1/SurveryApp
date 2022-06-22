package ba.etf.rma22.projekat.data.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Anketa::class, AnketaTaken::class, Grupa::class,
                    Istrazivanje::class, Odgovor::class, Pitanje::class, Opcije::class), version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun anketaDao(): AnketaDAO
    abstract fun grupaDao(): GrupaDAO
    abstract fun istrazivanjeDao(): IstrazivanjeDAO
    abstract fun odgovorDao(): OdgovorDAO
    abstract fun pitanjeDao(): PitanjeDAO
    abstract fun takeAnketaDao(): TakeAnketaDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA22DB"
            ).fallbackToDestructiveMigration().build()
    }
}