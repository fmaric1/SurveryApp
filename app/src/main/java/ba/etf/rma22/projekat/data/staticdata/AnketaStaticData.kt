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
            Anketa("Anketa 1", "RMA", Date(122, 3, 10), Date(122, 3, 12),
                    Date(122,3,11), 5, "RMA1", 0.33F, statusAnkete.AKTIVAN_URADEN),

            Anketa("Anketa 2", "RPR", Date(122, 3, 10), Date(122, 3, 21),
                    Date(), 2, "RPR1", 0.0F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 3", "RPR", Date(122, 3, 8), Date(122, 3, 10),
                    Date(), 1, "RPR2", 0.0F, statusAnkete.PROSAO),

            Anketa("Anketa 4", "TP", Date(122, 3, 10), Date(122, 3, 12),
                    Date(), 10, "TP1", 0.0F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 5", "TP", Date(122, 3, 9), Date(122, 3, 11),
                    Date(), 3, "TP2", 0.0F, statusAnkete.PROSAO),

            Anketa("Anketa 6", "VI", Date(122, 3, 12), Date(122, 3, 15),
                    Date(), 4, "VI1", 0.0F, statusAnkete.AKTIVAN_NIJE_URADEN),

            Anketa("Anketa 7", "VI", Date(122, 3, 15), Date(122, 3, 16),
                    Date(), 6, "VI2", 0F, statusAnkete.NEAKTIVAN)

    )
}