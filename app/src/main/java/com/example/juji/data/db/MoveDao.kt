package com.example.juji.data.db

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.juji.model.Move
import kotlinx.coroutines.flow.Flow

@Dao
interface MoveDao {

    @Query("SELECT * FROM Move")
    fun getAllMoves(): Flow<List<Move>>

    @Insert
    suspend fun insertMove(move: Move)

    @Update
    suspend fun updateMove(newMove: Move)

    @Query("DELETE FROM Move WHERE id = :id ")
    suspend fun deleteMove(id: Int)

}