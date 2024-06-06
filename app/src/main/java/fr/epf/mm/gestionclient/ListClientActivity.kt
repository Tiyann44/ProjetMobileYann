package fr.epf.mm.gestionclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Client
import fr.epf.mm.gestionclient.model.Gender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val TAG = "ListClientActivity"

class ListClientActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_client)

        recyclerView =
            findViewById<RecyclerView>(R.id.list_client_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_clients, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_client -> {
                startActivity(Intent(this, AddClientActivity::class.java))
            }
            R.id.action_synchro -> {
                synchro()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun synchro() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val userService =
            retrofit.create(RandomUserService::class.java)

//        CoroutineScope(Dispatchers.IO).launch {
//            val users = userService.getUsers(15)
//        }
//
        runBlocking {
            val users = userService.getUsers(15)
            Log.d(TAG, "synchro: ${users}")

            val clients = users.results.map {
                Client(
                    it.name.last, it.name.first,
                    if (it.gender == "male") Gender.MAN else Gender.WOMAN
                )
            }

            val adapter = ClientAdapter(clients)

            recyclerView.adapter = adapter

        }



    }
}