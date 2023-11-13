package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val country = retrofit.create(JsonAPI::class.java)

        val call : Call<List<DataModel>> = country.getPosts()
        call.enqueue(object : Callback<List<DataModel>>{
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                if (!response.isSuccessful){
                    tv.text = response.code().toString()
                    return
                }
                val posts : List<DataModel>? = response.body()
                if (posts != null) {
                    for (api : DataModel in posts){
                        var content = "Страна: "
                        content += api.name + " "
                        content += " Население: "
                        content += api.population + "\n"
                        tv.append(content)
                    }
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                tv.text = t.message
            }
        })
    }
}