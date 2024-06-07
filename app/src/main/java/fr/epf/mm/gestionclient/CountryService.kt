package fr.epf.mm.gestionclient

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomCountryService {
    @GET("countryInfo")
suspend  fun getCountry(@Query("Result") size: Int): GetCountriesResult
}

data class GetCountriesResult(val results: List<Country>)
data class Country(val postalcode: String,
                   val name: String,
                   val countryCode: String,)
