package com.example.juji.data

import com.example.juji.data.db.MoveDao
import com.example.juji.model.Move
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


object MoveRepository {

    lateinit var dao : MoveDao

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val moves: StateFlow<List<Move>> by lazy { dao.getAllMoves().stateIn(scope, SharingStarted.Lazily, emptyList()) }

    fun getMoveById(id: Int): Move? = moves.value.find {it.id == id}

    suspend fun addMove(newMove: Move){
        dao.insertMove(newMove)
    }

    suspend fun updateMove(id: Int, newMove: Move){
        val move = newMove.copy(id = id)
        dao.updateMove(move)
    }

    suspend fun deleteMove(id: Int){
        dao.deleteMove(id)
    }
}
