package fr.epf.mm.gestionclient

import android.util.Log
import android.view.View
import fr.epf.mm.gestionclient.model.Country

fun Country.getImage() = R.drawable.man

val Country.nomComplet : String
    get() = "${name} ${postalcode.mettreEnMajuscule()}"

fun String.mettreEnMajuscule() = this.uppercase()

fun View.click( action : (View) -> Unit){
    Log.d("CLICK","click !")
    this.setOnClickListener(action)
}