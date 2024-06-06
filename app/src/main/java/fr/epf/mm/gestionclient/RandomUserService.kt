package fr.epf.mm.gestionclient

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api")
suspend  fun getUsers(@Query("results") size: Int) : GetUsersResult
}

data class GetUsersResult(val results: List<User>)
data class User(val gender: String, val name: Name)
data class Name(val last: String, val first: String)
