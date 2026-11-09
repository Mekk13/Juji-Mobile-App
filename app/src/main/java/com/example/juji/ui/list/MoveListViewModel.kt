package com.example.juji.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juji.data.MoveRepository
import com.example.juji.model.Move
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MoveListViewModel : ViewModel() {
    private val repo = MoveRepository

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> =  _query
    val moves: StateFlow<List<Move>> = combine(repo.moves, _query)
    { moves, query ->
        moves.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),
    emptyList())



    fun updateQuery(newQuery: String){
        _query.value = newQuery
    }


    fun deleteMove(id: Int){
        repo.deleteMove(id)
    }
}