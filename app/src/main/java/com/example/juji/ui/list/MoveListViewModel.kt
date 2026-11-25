package com.example.juji.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

class MoveListViewModel : ViewModel() {
    private val repo = MoveRepository

    val loadError: StateFlow<String?> = repo.loadError

    var deleteError by mutableStateOf<String?>(null)
        private set
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
        viewModelScope.launch {
            repo.deleteMove(id)
                .onSuccess { deleteError = null }
                .onFailure { deleteError = "Could not delete move" }
        }

    }
}