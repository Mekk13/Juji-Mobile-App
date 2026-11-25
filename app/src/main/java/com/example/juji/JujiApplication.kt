package com.example.juji

import android.app.Application
import com.example.juji.data.MoveRepository
import com.example.juji.data.db.JujiDatabase

class JujiApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        val db : JujiDatabase = JujiDatabase.getDatabase(this)
        MoveRepository.dao = db.dao
    }


}