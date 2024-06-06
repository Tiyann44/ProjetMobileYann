package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

enum class Gender {
    MAN, WOMAN
}

@Parcelize
data class Client(
    val lastName: String,
    val firstName: String,
    val gender: Gender
) : Parcelable{
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Client("Nom${it}",
                    "Prénom${it}",
                    if(it % 3 == 0) Gender.MAN else Gender.WOMAN)
            }
    }
}