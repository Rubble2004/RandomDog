package com.example.a1801

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import com.bumptech.glide.Glide
import android.util.Log

import com.example.a1801.models.API.base_url
import com.example.a1801.models.API.ApiRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    lateinit var ImageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageView = findViewById(R.id.img)
    }

    fun update(view: View) {
        val fab =  findViewById<FloatingActionButton>(R.id.fab)
        fab.animate().rotationBy(360f).apply {
            duration=1000
            start()
        }
    makeApiRequest()
    }


    fun makeApiRequest(){
        val api = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getRandomDog()
                if (response.fileSizeBytes <400000) {
                    withContext(Dispatchers.Main){
                        Glide.with(applicationContext).load(response.url).into(ImageView)
                    }
                }
                else
                    makeApiRequest()
            }
            catch (e:Exception){
                Log.e("Main", "Error: ${e.message}")
            }
        }
}
}
