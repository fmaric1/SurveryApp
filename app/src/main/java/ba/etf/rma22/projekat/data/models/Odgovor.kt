package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("odgovoreno") val odgovoreno: Int,
    @SerializedName("anketa") val idAnketaTaken: Int = 0
)
