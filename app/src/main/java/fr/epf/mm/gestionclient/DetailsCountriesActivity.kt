package fr.epf.mm.gestionclient

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import fr.epf.mm.gestionclient.model.Country

class DetailsCountriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_client)

        val NameTextView =
            findViewById<TextView>(R.id.details_country_name_textview)

        val imageView = findViewById<ImageView>(R.id.details_country_imageview)

        intent.extras?.apply {
            val country = getParcelable(COUNTRY_ID_EXTRA) as? Country

            country?.let {
                NameTextView.text = it.postalcode
                imageView.setImageResource(country.getImage())
            }
        }



        val getImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result?.data?.extras?.getParcelable("data") as? Bitmap
                imageView.setImageBitmap(bitmap)
                Toast.makeText(this, "Photo mise à jour", Toast.LENGTH_SHORT).show()

            }
        }

        imageView.click {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getImage.launch(intent)
        }

        val favoritesButton = findViewById<Button>(R.id.favorites_button)
        favoritesButton.click {
            val intent = Intent(this, ListCountriesActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "country added to favorites", Toast.LENGTH_LONG).show()
    }
}
}