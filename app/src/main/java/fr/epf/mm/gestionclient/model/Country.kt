package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Country(
    val postalcode: String,
    val name: String,
    val countryCode: String,
) : Parcelable{
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Country("postalcode${it}",
                    "name${it}",
                    "countryCode${it}"
                    )
            }
    }
}