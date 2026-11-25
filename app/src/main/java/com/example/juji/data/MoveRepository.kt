package com.example.juji.data

import android.util.Log
import com.example.juji.data.db.MoveDao
import com.example.juji.model.Move
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn


object MoveRepository {

    lateinit var dao : MoveDao

    private val _loadError = MutableStateFlow<String?>(null)
    val loadError: StateFlow<String?> = _loadError
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val moves: StateFlow<List<Move>> by lazy {
        dao.getAllMoves()
            .catch{ e->
                Log.e("MoveRepository", "Could not load moves", e)
                _loadError.value = "Could not load moves"
            }
            .stateIn(scope, SharingStarted.Lazily, emptyList()) }

    fun getMoveById(id: Int): Move? = moves.value.find {it.id == id}

    suspend fun addMove(newMove: Move) : Result<Unit>{
        return try{
            dao.insertMove(newMove)
            Result.success(Unit)
        } catch( e: Exception){
            Log.e("MoveRepository", "Could not add move", e)
            Result.failure(e)
        }

    }

    suspend fun updateMove(id: Int, newMove: Move) : Result<Unit>{
        return try {
            val move = newMove.copy(id = id)
            dao.updateMove(move)
            Result.success(Unit)
        }catch (e: Exception){
            Log.e("MoveRepository", "Could not update move", e)
            Result.failure(e)
        }
    }

    suspend fun deleteMove(id: Int) : Result<Unit> {
        return try {
            dao.deleteMove(id)
            Result.success(Unit)
        }catch (e: Exception){
            Log.e("MoveRepository", "Could not delete move", e)
            Result.failure(e)
        }
    }
}
