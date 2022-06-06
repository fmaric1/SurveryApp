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

    private lateinit var mListener : onItemClickListener

   interface onItemClickListener{
       fun onItemClick(position: Int)
   }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anketa_item, parent, false)
        return AnketeViewHolder(view, mListener)
    }
    override fun getItemCount(): Int = ankete.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnketeViewHolder, position: Int) {
        val anketa = ankete[position]
        var datum = anketa.datumKraj
        var datumText = "Anketa urađena: "
        var progressInt = 0
        when(anketa.status){
            statusAnkete.AKTIVAN_URADEN -> {
                holder.status.setImageResource(R.drawable.plava)
            }
            statusAnkete.AKTIVAN_NIJE_URADEN -> {
                holder.status.setImageResource(R.drawable.zelena)
                datumText = "Vrijeme zatvaranja: "
            }
            statusAnkete.NEAKTIVAN -> {
                holder.status.setImageResource(R.drawable.zuta)
                datum = anketa.datumPocetak
                datumText = "Vrijeme aktiviranja: "
            }
            statusAnkete.PROSAO ->{
                holder.status.setImageResource(R.drawable.crvena)
                datumText = "Anketa zatvorena: "
            }
        }
        if(anketa.progress<0.1)
            progressInt = 0
        else if(anketa.progress>=0.1 && anketa.progress<0.3)
            progressInt = 20
        else if(anketa.progress>=0.3 && anketa.progress<0.5)
            progressInt = 40
        else if(anketa.progress>=0.5 && anketa.progress<0.7)
            progressInt = 60
        else if(anketa.progress>=0.7 && anketa.progress<1.0)
            progressInt = 80
        else if(anketa.progress>=1.0)
            progressInt = 100
        holder.imeKviza.text = anketa.naziv
        if(datum != null)
        holder.datum.text = datumText + datum.date.toString()+"."+ (datum.month +1).toString() + "." + (datum.year + 1900).toString()
        else{
            holder.datum.text = ""
        }
        holder.imePredmeta.text = anketa.nazivIstrazivanja
        holder.progressBar.progress = progressInt

    }
    fun updateAnkete(ankete: List<Anketa>) {
        this.ankete = ankete
        notifyDataSetChanged()
    }

    inner class AnketeViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val status: ImageView = itemView.findViewById(R.id.anketaStatus)
        val imeKviza: TextView = itemView.findViewById(R.id.imeAnkete)
        val imePredmeta: TextView = itemView.findViewById(R.id.nazivIstrazivanja)
        val datum: TextView = itemView.findViewById(R.id.anketaDatum)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}


