package fr.epf.mm.gestionclient

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import fr.epf.mm.gestionclient.model.Client

class DetailsClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_client)

        val lastNameTextView =
            findViewById<TextView>(R.id.details_client_lastname_textview)

        val imageView = findViewById<ImageView>(R.id.details_client_imageview)

        intent.extras?.apply {
            val client = getParcelable(CLIENT_ID_EXTRA) as? Client

            client?.let {
                lastNameTextView.text = it.lastName
                imageView.setImageResource(client.getImage())
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
    }
}