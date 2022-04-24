package ba.etf.rma22.projekat.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.getAnkete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import ba.etf.rma22.projekat.data.staticdata.getGrupe
import ba.etf.rma22.projekat.data.staticdata.getIstrazivanja
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class AnketaRepository {

    companion object {
        val mojeAnkete = ArrayList<Anketa>()
        var sveAnkete = ArrayList<Anketa>()
        init {
            val Ankete = getAnkete()
            for(x in GrupaRepository.getMyGroups()){
                mojeAnkete.addAll(Ankete.filter { it.nazivGrupe == x.naziv })
            }
            sveAnkete.addAll(getAnkete())
            izbaciMojeAnkete()

        }

        fun getMyAnkete(): List<Anketa>{
            return mojeAnkete
        }

        fun getAll(): List<Anketa> {
            return mojeAnkete + sveAnkete
        }

        fun getDone(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.AKTIVAN_URADEN) }
        }

        fun getFuture(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.NEAKTIVAN) }
        }

        fun getNotTaken(): List<Anketa> {
            return getMyAnkete().filter { it.status.equals(statusAnkete.PROSAO) }
        }

        fun dodajAnketu(){
            val nazivAnketa: ArrayList<String> = ArrayList()
            for (x in mojeAnkete){
                nazivAnketa.add(x.naziv)
            }
            for(x in getAnkete()){
                for(y in GrupaRepository.getMyGroups()){
                    if(x.nazivGrupe == y.naziv && !nazivAnketa.contains(x.naziv))
                        mojeAnkete.add(x)
                }
            }
            izbaciMojeAnkete()

        }
        fun izbaciMojeAnkete(){
            val nazivAnketa: ArrayList<String> = ArrayList()
            for (x in mojeAnkete){
                nazivAnketa.add(x.naziv)
            }
            val filtriraneAnkete: ArrayList<Anketa> = ArrayList()
            for(x in sveAnkete){
                if(!nazivAnketa.contains(x.naziv))
                    filtriraneAnkete.add(x)
            }
            sveAnkete =filtriraneAnkete
        }

        fun updateProgressAnkete(imeAnkete: String, nazivIstrazivanja: String){
            val pitanja = PitanjeAnketaRepository.getPitanja(imeAnkete, nazivIstrazivanja)
            var brojac: Int = 0
            for(x in pitanja){
                if(PitanjeAnketaRepository.dajOdgovor(x.naziv) != 0)
                    brojac++
            }

            for(x in mojeAnkete){
                if(x.naziv == imeAnkete && pitanja.isNotEmpty()){
                    x.progress=(brojac.toFloat()/pitanja.size)
                }
            }
        }
        fun updateStatusAnkete(imeAnkete: String){
            for(x in mojeAnkete){
                if(x.naziv == imeAnkete){
                    x.status = statusAnkete.AKTIVAN_URADEN
                }
            }
        }

        fun updateDatumRada(naziv: String){
            for(x in mojeAnkete)
                if(x.naziv == naziv) {
                    val date = Date()
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    x.datumRada = calendar.time
                }
        }

        fun dajProgress(naziv: String): Float {
            for(x in mojeAnkete)
                if(x.naziv == naziv)
                    return x.progress
            return (0).toFloat()
        }
        fun dajStatusAnkete(naziv: String): statusAnkete{
            for(x in mojeAnkete){
                if(x.naziv == naziv)
                    return x.status
            }
            return statusAnkete.AKTIVAN_URADEN
        }


    }
}