package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import java.io.Serializable


class AnketaListAdapter(private var ankete: List<Anketa>) : RecyclerView.Adapter<AnketaListAdapter.AnketeViewHolder>(), Serializable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anketa_item, parent, false)
        return AnketeViewHolder(view)
    }
    override fun getItemCount(): Int = ankete.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnketeViewHolder, position: Int) {
        val anketa = ankete[position]
        var datum = anketa.datumKraj
        when(anketa.status){
            statusAnkete.AKTIVAN_URADEN -> {
                holder.status.setImageResource(R.drawable.plava)
                datum = anketa.datumRada!!
            }
            statusAnkete.AKTIVAN_NIJE_URADEN -> holder.status.setImageResource(R.drawable.zelena)
            statusAnkete.NEAKTIVAN -> {
                holder.status.setImageResource(R.drawable.zuta)
                datum = anketa.datumPocetka
            }
            statusAnkete.PROSAO -> holder.status.setImageResource(R.drawable.crvena)
        }
        holder.imeKviza.text = anketa.naziv
        holder.datum.text = datum.date.toString()+"."+ (datum.month +1).toString() + "." + (datum.year + 1900).toString()
        holder.imePredmeta.text = anketa.nazivIstrazivanja
        holder.progressBar.progress = (anketa.progress*100).toInt()

    }
    fun updateAnkete(ankete: List<Anketa>) {
        this.ankete = ankete
        notifyDataSetChanged()
    }

    inner class AnketeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: ImageView = itemView.findViewById(R.id.anketaStatus)
        val imeKviza: TextView = itemView.findViewById(R.id.imeAnkete)
        val imePredmeta: TextView = itemView.findViewById(R.id.nazivIstrazivanja)
        val datum: TextView = itemView.findViewById(R.id.anketaDatum)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
    }

}


