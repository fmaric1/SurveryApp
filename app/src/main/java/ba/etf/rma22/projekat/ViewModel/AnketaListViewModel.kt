package ba.etf.rma22.projekat.ViewModel

import android.app.Application
import android.content.Context
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*
import java.io.Serializable

class AnketaListViewModel(application: Application) : Serializable{

    val context = application.applicationContext
    init {
        val anketaDao = AppDatabase.getInstance(application.applicationContext).anketaDao()

    }

    fun getMyAnkete():List<Anketa>{
        return AnketaRepository.getMyAnkete()
    }
    fun getAll():List<Anketa>{
        return AnketaRepository.getAllFromRepository()
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

    fun getUpisanaIstrazivanja(): List<Istrazivanje> {
        return IstrazivanjeIGrupaRepository.getUpisanaIstrazivanja()
    }

    fun getIstrazivanjaByGodina(godina: Int): List<Istrazivanje>{
        return IstrazivanjeIGrupaRepository.getIstrazivanjeByGodina(godina)
    }

    fun getGrupe(istrazivanje: String): List<Grupa> {
        return IstrazivanjeIGrupaRepository.getGroupsByIstrazivanja(istrazivanje)
    }

    fun upisiStudenta(toString: String, istrazivanje: Istrazivanje, grupa: Grupa) {
        GrupaRepository.upisiStudenta(grupa)
        IstrazivanjeRepository.upisiIstrazivanje(istrazivanje)

    }
    suspend fun upisiStudenta(id: Int){
        IstrazivanjeIGrupaRepository.upisiUGrupu(id)
    }

    fun getPitanjaAnkete(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
        return PitanjeAnketaRepository.getPitanja(nazivAnkete, nazivIstrazivanja )
    }
    suspend fun getPitanjaAnkete(idAnkete: Int): ArrayList<Pitanje>{
        val pitanja = ArrayList<Pitanje>()
        for(x in PitanjeAnketaRepository.getPitanja(idAnkete))
            pitanja.add(x)
        return pitanja
    }
    suspend fun dajPodatkeSaWebServisa(){
        AnketaRepository.getAll()
        IstrazivanjeIGrupaRepository.getGrupe()
        IstrazivanjeIGrupaRepository.getIstrazivanja()
        IstrazivanjeIGrupaRepository.grupeSaIstrazivanjima()
        for (x in IstrazivanjeIGrupaRepository.getUpisaneGrupe()){
            AnketaRepository.dodajMojeAnkete(x.naziv)
        }
        PitanjeAnketaRepository.dajSvaPitanja()
    }

    fun dodajAnketu(grupa: String) {
        AnketaRepository.dodajMojeAnkete(grupa)
    }

    suspend fun zapocniAnketu(id: Int) : AnketaTaken? {
        return TakeAnketaRepository.zapocniAnketu(id)

    }
    suspend fun posaljiOdgovore(){
        OdgovorRepository.posaljiOdgovore()
    }

    fun dajSveOdgovore(): List<Odgovor> {
        val odgovori = ArrayList<Odgovor>()
        odgovori.addAll(OdgovorRepository.odgovori)
        odgovori.addAll(OdgovorRepository.neposlaniOdgovori)
        return odgovori
    }
    fun postaviContext(){
        AnketaRepository.setCont(context)
        IstrazivanjeIGrupaRepository.setCont(context)
        OdgovorRepository.setCont(context)
        PitanjeAnketaRepository.setCont(context)
        TakeAnketaRepository.setCont(context)
        AccountRepository.setCont(context)
    }
    suspend fun ucitajIzBaze(){
        val db = AppDatabase.getInstance(context)
        IstrazivanjeIGrupaRepository.istrazivanjaRep = db.istrazivanjeDao().getAll() as ArrayList<Istrazivanje>
        IstrazivanjeIGrupaRepository.grupeRep = db.grupaDao().getAll() as ArrayList<Grupa>
        AnketaRepository.sveAnkete = db.anketaDao().getAll() as ArrayList<Anketa>
        TakeAnketaRepository.poceteAnkete = db.takeAnketaDao().getAll() as ArrayList<AnketaTaken>
        PitanjeAnketaRepository.svaPitanja = db.pitanjeDao().getAll() as ArrayList<Pitanje>
        OdgovorRepository.odgovori = db.odgovorDao().getAll() as ArrayList<Odgovor>
    }

}
