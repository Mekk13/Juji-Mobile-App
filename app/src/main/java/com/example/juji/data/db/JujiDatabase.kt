package com.example.juji.data.db

import android.content.Context
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.juji.model.Move

@Database(
    entities = [Move::class],
    version = 1,
    exportSchema = false
)
@ColumnTypeConverters(Converters::class)
abstract class JujiDatabase : RoomDatabase(){

    abstract val dao: MoveDao

    companion object{

        @Volatile
        private var Instance : JujiDatabase? = null

        fun getDatabase(context: Context): JujiDatabase {
            return Instance ?: synchronized(this){
                Instance ?: Room.databaseBuilder(context.applicationContext, JujiDatabase::class.java, "juji_database")
                            .fallbackToDestructiveMigration()
                            .build()
                            .also {Instance = it}
            }
        }
    }
}