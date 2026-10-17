package com.example.juji.data

import com.example.juji.model.Move
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate


object MoveRepository {

    private val _moves = MutableStateFlow<List<Move>>(
        listOf(
        Move(1,"RNC","Submission","opponent back",true, LocalDate.of(2026,6,29),100,4),
        Move(2, "Armbar", "Submission", "Mount or closed guard", true, LocalDate.of(2026, 6, 20), 85, 3),
        Move(3, "Triangle Choke", "Submission", "Closed guard", true, LocalDate.of(2026, 6, 18), 90, 4),
        Move(4, "Kimura", "Submission", "Side control or guard", true, LocalDate.of(2026, 6, 15), 75, 3),
        Move(5, "Guillotine", "Submission", "Front headlock", false),
        Move(6, "Americana", "Submission", "Side control", true, LocalDate.of(2026, 6, 10), 65, 2),
        Move(7, "Double Leg Takedown", "Takedown", "Standing", true, LocalDate.of(2026, 6, 8), 15, 3),
        Move(8, "Hip Escape", "Escape", "Side control bottom", true, LocalDate.of(2026, 6, 5), 20, 4)
        )
    )

    val moves: StateFlow<List<Move>> = _moves

    private var nextId  = 9

    fun getMoveById(id: Int): Move? = _moves.value.find {it.id == id}

    fun addMove(newMove: Move): Move{
        val move = newMove.copy(id = nextId)
        _moves.value += move
        nextId ++
        return move
    }

    fun updateMove(id: Int, newMove: Move){
        _moves.value = _moves.value.map { move -> if(move.id == id) newMove.copy(id = id) else move}
    }

    fun deleteMove(id: Int){
        _moves.value = _moves.value.filter { move -> move.id != id }
    }
}
