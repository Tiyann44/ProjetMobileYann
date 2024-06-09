package fr.epf.mm.gestionclient

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.epf.mm.gestionclient.model.Country

private const val TAG = "FavoritesActivity"

class FavoritesActivity : AppCompatActivity(), CountryListener {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById<RecyclerView>(R.id.activity_favorites_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadFavorites()
    }

    private fun loadFavorites() {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favorites = sharedPref.getStringSet("favorites_set", mutableSetOf()) ?: mutableSetOf()

        val gson = Gson()
        val countries = favorites.map { json ->
            gson.fromJson(json, Country::class.java)
        }

        val adapter = CountriesAdapter(countries, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_countries, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClickCountry(country: Country) {
        // Handle click event to navigate to country details
        val intent = Intent(this, DetailsCountriesActivity::class.java).apply {
            putExtra("CountryName", country.name)
            putExtra("CountryCapital", country.capital)
            putExtra("Flag", country.Flag)
        }
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
            }
            R.id.action_home -> {
                startActivity(Intent(this, ListCountriesActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
