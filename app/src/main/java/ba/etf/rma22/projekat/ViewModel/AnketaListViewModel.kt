package ba.etf.rma22.projekat.ViewModel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import java.io.Serializable

class AnketaListViewModel : Serializable{
    fun getMyAnkete():List<Anketa>{
        return AnketaRepository.getMyAnkete()
    }
    fun getAll():List<Anketa>{
        return AnketaRepository.getAll()
    }

    fun getDone():List<Anketa>{
        return AnketaRepository.getDone()
    }
    fun getFuture():List<Anketa>{
        return AnketaRepository.getFuture()
    }
    fun getNotTaken():List<Anketa>{
        return AnketaRepository.getNotTaken()
    }

    fun getIstrazivanja(): List<Istrazivanje> {
        return IstrazivanjeRepository.getAll()
    }

    fun getIstrazivanjaByGodina(godina: Int): List<Istrazivanje>{
        return IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
    }

    fun getGrupe(istrazivanje: String): List<Grupa> {
        return GrupaRepository.getGroupsByIstrazivanja(istrazivanje)
    }

    fun upisiStudenta(toString: String, istrazivanje: Istrazivanje, grupa: Grupa) {
        AnketaRepository.upisiStudenta(grupa)
        IstrazivanjeRepository.upisiIstrazivanje(istrazivanje)

    }

}