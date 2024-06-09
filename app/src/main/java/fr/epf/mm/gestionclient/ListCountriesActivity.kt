package fr.epf.mm.gestionclient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "ListCountriesActivity"

class ListCountriesActivity : AppCompatActivity(), CountryListener {
    private val geonamesUsername = "maxenceepf"
    lateinit var recyclerView: RecyclerView
    private var countriesList: List<Country> = listOf() // Liste des pays

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_countries)

        recyclerView = findViewById(R.id.list_countries_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_countries, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchCountry(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })

        return true
    }

    private fun searchCountry(query: String?) {
        // Logique de recherche pour trouver le pays correspondant au texte de la recherche
        val foundCountry = countriesList.find {
            it.name.equals(query, ignoreCase = true) ||
                    it.capital.equals(query, ignoreCase = true)
        }

        if (foundCountry != null) {
            val intent = Intent(this, DetailsCountriesActivity::class.java).apply {
                putExtra("CountryCode", foundCountry.countryCode)
                putExtra("CountryName", foundCountry.name)
                putExtra("CountryCapital", foundCountry.capital)
                putExtra("Flag", foundCountry.Flag)
            }
            startActivity(intent)
        } else {
            // Aucun pays correspondant trouvé
            // Vous pouvez afficher un message à l'utilisateur ou effectuer une autre action
            Log.e(TAG, "Aucun pays correspondant trouvé pour $query")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
            }

            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
            R.id.action_synchro -> {
                synchro()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun synchro() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.geonames.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val countryService = retrofit.create(RandomCountryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = countryService.getCountry(geonamesUsername)
                val countries = response.geonames.map {
                    Country(
                        it.countryCode, it.countryName, it.capital, it.Flag
                    )
                }

                // Mise à jour de la liste des pays
                countriesList = countries

                withContext(Dispatchers.Main) {
                    val adapter = CountriesAdapter(countries, this@ListCountriesActivity)
                    recyclerView.adapter = adapter
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error: ${e.code()}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error during synchro", e)
            }
        }
    }

    override fun onClickCountry(country: Country) {
        val intent = Intent(this, DetailsCountriesActivity::class.java).apply {
            putExtra("CountryCode", country.countryCode)
            putExtra("CountryName", country.name)
            putExtra("CountryCapital", country.capital)
            putExtra("Flag", country.Flag)
        }
        startActivity(intent)
    }
}
