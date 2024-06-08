package fr.epf.mm.gestionclient

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epf.mm.gestionclient.model.Country

//public class ClientViewHolder extends RecyclerView.ViewHolder{
//
//    public ClientViewHolder(View view){
//        super(view)
//    }

const val COUNTRY_ID_EXTRA = "countryId"

class CountriesViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val imageView = view.findViewById<ImageView>(R.id.country_view_imageview)
}

interface CountryListener{
    fun onClickCountry(country: Country)
}
class CountriesAdapter(val countries: List<Country>, val listener:CountryListener) : RecyclerView.Adapter<CountriesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountriesViewHolder(view)
    }

    override fun getItemCount() = countries.size


    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val clientNameTextView = view.findViewById<TextView>(R.id.country_view_textview)
//        clientNameTextView.text = "${client.firstName} ${client.lastName}"
        clientNameTextView.text = country.nomComplet


        Glide.with(view.context)
            .load(country.Flag)
            .into(holder.imageView)
view.setOnClickListener{listener.onClickCountry(country)
}
        val cardVIew = view.findViewById<CardView>(R.id.country_view_cardview)
        cardVIew.click {
            with(it.context){
                val intent = Intent(this, DetailsCountriesActivity::class.java)
                intent.putExtra(COUNTRY_ID_EXTRA, country)
                startActivity(intent)
            }
        }
    }
}


