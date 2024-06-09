package fr.epf.mm.gestionclient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)


        val documentationButton: Button = findViewById(R.id.documentation_button)
        documentationButton.setOnClickListener {

            val documentationUrl = "https://github.com/Tiyann44/CountryFinderDocumentation/blob/main/README.md"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(documentationUrl))
            startActivity(intent)
        }


        val getRickrolledButton: Button = findViewById(R.id.getRickrolled_button)
        getRickrolledButton.setOnClickListener {

            val rickrollUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rickrollUrl))
            startActivity(intent)
        }


        val buyMeACoffeeButton: Button = findViewById(R.id.buymeacoffee_button)
        buyMeACoffeeButton.setOnClickListener {

            Toast.makeText(this, "Thanks for the virtual coffee!", Toast.LENGTH_SHORT).show()
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

        }
        return super.onOptionsItemSelected(item)
    }
}
