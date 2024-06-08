package fr.epf.mm.gestionclient

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomCountryService {
    @GET("countryInfoJSON")
    suspend fun getCountry(
        @Query("username") username: String
    ): GetCountriesResult
}

data class GetCountriesResult(val geonames: List<CountryResp>)
data class CountryResp(val countryCode: String, val countryName: String, val capital: String) {
    val Flag: String
        get() = "https://flagcdn.com/w20/${countryCode.lowercase()}.png"
}
