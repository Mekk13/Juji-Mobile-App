package com.example.juji.ui.list

import androidx.lifecycle.ViewModel
import com.example.juji.data.MoveRepository
import com.example.juji.model.Move
import kotlinx.coroutines.flow.StateFlow

class MoveListViewModel : ViewModel() {
    private val repo = MoveRepository
    val moves: StateFlow<List<Move>> = repo.moves

    fun deleteMove(id: Int){
        repo.deleteMove(id)
    }
}