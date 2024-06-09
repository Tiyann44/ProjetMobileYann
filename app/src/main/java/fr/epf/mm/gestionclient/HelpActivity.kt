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

        // Bouton "Read the documentation"
        val documentationButton: Button = findViewById(R.id.documentation_button)
        documentationButton.setOnClickListener {
            // URL de la documentation
            val documentationUrl = "https://github.com/Tiyann44/CountryFinderDocumentation/blob/main/README.md"
            // Intention pour ouvrir le navigateur
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(documentationUrl))
            startActivity(intent)
        }

        // Vous pouvez ajouter des écouteurs de clic pour les autres boutons ici
        // Bouton "get Rickrolled"
        val getRickrolledButton: Button = findViewById(R.id.getRickrolled_button)
        getRickrolledButton.setOnClickListener {
            // URL de la vidéo Rickroll
            val rickrollUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
            // Intention pour ouvrir le navigateur
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rickrollUrl))
            startActivity(intent)
        }

        // Bouton "Buy me a virtual coffee"
        val buyMeACoffeeButton: Button = findViewById(R.id.buymeacoffee_button)
        buyMeACoffeeButton.setOnClickListener {
            // Afficher un toast
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
