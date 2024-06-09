package fr.epf.mm.gestionclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.epf.mm.gestionclient.model.Country

class DetailsCountriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_client)

        val countryCode = intent.getStringExtra("countryCode")
        val flag = intent.getStringExtra("Flag")
        val countryName = intent.getStringExtra("CountryName")
        val countryCapital = intent.getStringExtra("CountryCapital")

        val countryCodeTextView = findViewById<TextView>(R.id.details_country_code_textview)
        val nameTextView = findViewById<TextView>(R.id.details_country_name_textview)
        val capitalTextView = findViewById<TextView>(R.id.details_country_capital_textview)
        val imageView = findViewById<ImageView>(R.id.details_country_imageview)

        countryCodeTextView.text = countryCode
        nameTextView.text = countryName
        capitalTextView.text = countryCapital

        Glide.with(this).load(flag).into(imageView)

        val getImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result?.data?.extras?.getParcelable("data") as? Bitmap
                imageView.setImageBitmap(bitmap)
                Toast.makeText(this, "Photo mise à jour", Toast.LENGTH_SHORT).show()
            }
        }

        imageView.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            getImage.launch(intent)
        }

        val favoritesButton = findViewById<Button>(R.id.favorites_button)
        favoritesButton.setOnClickListener {
            addCountryToFavorites(countryCode, countryName, countryCapital, flag)
            Toast.makeText(this, "Country added to favorites", Toast.LENGTH_LONG).show()
        }
        val deleteFavoritesButton = findViewById<Button>(R.id.delete_favorites_button)
        deleteFavoritesButton.setOnClickListener {
            deleteCountryFromFavorites()
        }
    }

    private fun addCountryToFavorites(
        countryCode: String?,
        name: String?,
        capital: String?,
        flag: String?
    ) {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favorites = sharedPref.getStringSet("favorites_set", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()

        val country = Country(countryCode ?: "", name ?: "", capital ?: "", flag ?: "")
        val gson = Gson()
        favorites.add(gson.toJson(country))

        with(sharedPref.edit()) {
            putStringSet("favorites_set", favorites)
            apply()
        }
    }

    private fun deleteCountryFromFavorites() {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val gson = Gson()
        val country = Country(
            intent.getStringExtra("CountryCode") ?: "",
            intent.getStringExtra("CountryName") ?: "",
            intent.getStringExtra("CountryCapital") ?: "",
            intent.getStringExtra("Flag") ?: ""
        )
        val countryJson = gson.toJson(country)

        val favorites = sharedPref.getStringSet("favorites_set", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()

        if (favorites.contains(countryJson)) {
            favorites.remove(countryJson)
            with(sharedPref.edit()) {
                putStringSet("favorites_set", favorites)
                apply() // Persist the changes
            }
            Toast.makeText(this, "Country removed from favorites", Toast.LENGTH_LONG).show()
            // Redirect to FavoritesActivity
            startActivity(Intent(this, FavoritesActivity::class.java))
            finish() // Finish the current activity to remove it from the back stack
        } else {
            Toast.makeText(this, "Country is not in favorites", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_countries, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
            R.id.action_home -> {
                startActivity(Intent(this, ListCountriesActivity::class.java))
            }
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
