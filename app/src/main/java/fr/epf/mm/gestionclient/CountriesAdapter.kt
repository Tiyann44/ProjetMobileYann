package fr.epf.mm.gestionclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epf.mm.gestionclient.model.Country

const val COUNTRY_ID_EXTRA = "countryId"

class CountriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.country_view_imageview)
}

interface CountryListener {
    fun onClickCountry(country: Country)
}

class CountriesAdapter(
    private val countries: List<Country>,
    private val listener: CountryListener
) : RecyclerView.Adapter<CountriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountriesViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val countryNameTextView = view.findViewById<TextView>(R.id.country_view_textview)
        countryNameTextView.text = country.name

        Glide.with(view.context)
            .load(country.Flag)
            .into(holder.imageView)

        view.setOnClickListener {
            listener.onClickCountry(country)
        }
    }
}
