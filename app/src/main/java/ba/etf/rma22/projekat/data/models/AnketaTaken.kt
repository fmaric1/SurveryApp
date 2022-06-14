package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class AnketaTaken(
    @PrimaryKey@SerializedName("id") val id: Int,
    @SerializedName("student") val student: String,
    @SerializedName("progres") val progres: Int,
    @SerializedName("datumRada") val datumRada: Date,
    @SerializedName("AnketumId") val AnketumId: Int
)
