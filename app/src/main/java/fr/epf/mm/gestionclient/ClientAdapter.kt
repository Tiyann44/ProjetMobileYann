package fr.epf.mm.gestionclient

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Client
import fr.epf.mm.gestionclient.model.Gender

//public class ClientViewHolder extends RecyclerView.ViewHolder{
//
//    public ClientViewHolder(View view){
//        super(view)
//    }

const val CLIENT_ID_EXTRA = "clientId"

class ClientViewHolder(view : View) : RecyclerView.ViewHolder(view)


class ClientAdapter(val clients: List<Client>) : RecyclerView.Adapter<ClientViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.client_view, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount() = clients.size


    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        val view = holder.itemView
        val clientNameTextView = view.findViewById<TextView>(R.id.client_view_textview)
//        clientNameTextView.text = "${client.firstName} ${client.lastName}"
        clientNameTextView.text = client.nomComplet

        val imageView = view.findViewById<ImageView>(R.id.client_view_imageview)
//        imageView.setImageResource(
//            if(client.gender == Gender.MAN) R.drawable.man else R.drawable.woman
//        )
        imageView.setImageResource(client.getImage())

        val cardVIew = view.findViewById<CardView>(R.id.client_view_cardview)
        cardVIew.click {
            with(it.context){
                val intent = Intent(this, DetailsClientActivity::class.java)
                intent.putExtra(CLIENT_ID_EXTRA, client)
                startActivity(intent)
            }
        }
    }
}


