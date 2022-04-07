package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.statusAnkete
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.*

class UnitTests {
    val anketa1 = Anketa("Anketa 1", "RMA", Date(122, 3, 10), Date(122, 3, 12),
        Date(122,3,11), 5, "RMA1", 1F, statusAnkete.AKTIVAN_URADEN)
    val anketa2 = Anketa("Anketa 2", "RPR", Date(122, 3, 10), Date(122, 3, 21),
    Date(), 2, "RPR1", 0.0F, statusAnkete.AKTIVAN_NIJE_URADEN)
    val anketa3 = Anketa("Anketa 3", "RPR", Date(122, 3, 8), Date(122, 3, 10),
    Date(), 1, "RPR2", 0.51F, statusAnkete.PROSAO)
    val anketa4 = Anketa("Anketa 4", "TP", Date(122, 3, 10), Date(122, 3, 12),
    Date(), 10, "TP1", 0.3F, statusAnkete.AKTIVAN_NIJE_URADEN)
    val anketa5 = Anketa("Anketa 5", "TP", Date(122, 3, 9), Date(122, 3, 11),
    Date(), 3, "TP2", 1F, statusAnkete.PROSAO)
    val anketa6 = Anketa("Anketa 6", "VI", Date(122, 3, 12), Date(122, 3, 15),
    Date(), 4, "VI1", 0.5F, statusAnkete.AKTIVAN_NIJE_URADEN)
    val anketa7 = Anketa("Anketa 7", "VI", Date(122, 3, 15), Date(122, 3, 16),
    Date(), 6, "VI2", 0F, statusAnkete.NEAKTIVAN)
    var ankete = AnketaRepository.getAll()

    @Test
    fun getMyAnketeTest(){
        ankete = AnketaRepository.getMyAnkete()
        assertEquals(1, ankete.size)
        assertTrue(ankete.contains(anketa1))
    }
    @Test
    fun getAllAnketeTest(){
        ankete = AnketaRepository.getAll()
        assertEquals(7, ankete.size)
    }

    @Test
    fun getDoneTest(){
        ankete = AnketaRepository.getDone()
        assertEquals(1, ankete.size)
        assertTrue(ankete.contains(anketa1))
    }

    @Test
    fun getFutureTest(){
        ankete = AnketaRepository.getFuture()
        assertEquals(1, ankete.size)
        assertTrue(ankete.contains(anketa7))
    }

    @Test
    fun getNotTakenTest(){
        ankete = AnketaRepository.getNotTaken()
        assertEquals(2, ankete.size)
        assertTrue(ankete.contains(anketa3))
        assertTrue(ankete.contains(anketa5))
    }

    @Test
    fun upisiStudentaTest(){
        val grupa = Grupa("TP1", "TP")
        val istrazivanje = Istrazivanje("TP", 2)
        IstrazivanjeRepository.upisiIstrazivanje(istrazivanje)
        AnketaRepository.upisiStudenta(grupa)
        ankete = AnketaRepository.getMyAnkete()
        assertEquals(2, ankete.size)

    }






}