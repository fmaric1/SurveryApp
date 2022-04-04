package ba.etf.rma22.projekat.data.staticdata

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import java.time.LocalDate
import java.time.Month
import java.util.*

fun getAnkete(): List<Anketa> {
    return listOf(
            Anketa("Anketa 1", "RMA", Date(121, 3, 10), Date(121, 3, 12),
                    Date(121,3,11), 5, "RMA1", 0.4F, statusAnkete.AKTIVAN_URADEN),

            Anketa("Anketa 2", "RPR", Date(121, 3, 10), Date(121, 3, 21),
                    Date(), 2, "RPR1", 0.6F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 3", "RPR", Date(121, 3, 8), Date(121, 3, 10),
                    Date(), 1, "RPR2", 1F, statusAnkete.PROSAO),

            Anketa("Anketa 4", "TP", Date(121, 3, 10), Date(121, 3, 12),
                    Date(), 10, "TP1", 0F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 5", "TP", Date(121, 3, 9), Date(121, 3, 11),
                    Date(), 3, "TP2", 1F, statusAnkete.PROSAO),

            Anketa("Anketa 1", "VI", Date(121, 3, 12), Date(121, 3, 15),
                    Date(), 4, "VI1", 0.5F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 1", "VI", Date(121, 3, 15), Date(121, 3, 16),
                    Date(), 6, "VI2", 0.7F, statusAnkete.NEAKTIVAN)

    )
}