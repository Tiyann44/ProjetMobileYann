package fr.epf.mm.gestionclient

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api")
suspend  fun getUsers(@Query("results") size: Int) : GetUsersResult
}

data class GetUsersResult(val results: List<Country>)
data class Country(val gender: String, val name: Name)
data class Name(val last: String, val first: String)

data class postalCode(val postal: String)

data class countryCode(val countrycode: String)
