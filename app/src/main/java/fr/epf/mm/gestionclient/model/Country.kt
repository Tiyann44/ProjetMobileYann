package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Country(
    val countryCode: String,
    val name: String,
    val Flag : String
) : Parcelable{
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Country("countryCode${it}",
                    "name${it}",
                    "Flag${it}"

                    )
            }
    }
}