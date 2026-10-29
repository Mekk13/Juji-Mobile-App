package com.example.juji.ui.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.juji.data.MoveRepository
import com.example.juji.model.Move
import com.example.juji.model.MoveFormState
import com.example.juji.ui.navigation.EditMoveRoute
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class EditMoveViewModel(savedStateHandle: SavedStateHandle): ViewModel(){

    private val id = savedStateHandle.toRoute<EditMoveRoute>().id
    private val repo = MoveRepository
    var form by mutableStateOf(toFormState(repo.getMoveById(id)))
        private set

    val move: Move? = repo.getMoveById(id)

    private fun toFormState(move: Move?): MoveFormState{
        if (move == null) return MoveFormState()

        return MoveFormState(
            name = move.name,
            category = move.category,
            startPos = move.startingPosition ?: "",
            attempted = move.attempted,
            timesPracticed = if (move.timesPracticed == 0 ) "" else move.timesPracticed.toString()  ,
            proficiency = move.proficiency?.toString() ?: "",
            lastAttempt = move.lastAttempt
        )
    }

    fun updateForm(newForm: MoveFormState){
        form = newForm
    }

    fun updateLastAttempt(millis: Long?){
        if (millis != null) {
            val date = Instant.ofEpochMilli(millis)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()
            form = form.copy(lastAttempt = date)
        }
    }

    fun saveMove(): Boolean{

        val nameError = if(form.name.isBlank()) "Name is required" else null
        val categoryError = if (form.category.isBlank()) "Category is required" else null

        val times = form.timesPracticed.toIntOrNull()
        val timesPracticedError =
            if (form.attempted && form.timesPracticed.isNotBlank() && (times == null || times < 1)) "Enter a whole number, at least 1" else null

        val proficiency = form.proficiency.toIntOrNull()
        val proficiencyError =
            if (form.attempted && form.proficiency.isNotBlank() && (proficiency == null || proficiency !in 1..10)) "Enter a whole number from 1 to 10" else null

        val lastAttempt = form.lastAttempt
        val lastAttemptError =
            if (form.attempted && lastAttempt != null && lastAttempt.isAfter(LocalDate.now())) "Date can't be in the future" else null

        form = form.copy(
            nameError = nameError,
            categoryError = categoryError,
            timesPracticedError = timesPracticedError,
            proficiencyError = proficiencyError,
            lastAttemptError = lastAttemptError
        )

        if (nameError != null || categoryError != null || timesPracticedError != null ||
            proficiencyError != null || lastAttemptError != null) {
            return false
        }

        val move = Move(
            id = 0,
            name = form.name.trim(),
            category = form.category.trim(),
            startingPosition = form.startPos.trim().ifBlank { null },
            attempted = form.attempted,
            lastAttempt = if (form.attempted) form.lastAttempt else null,
            timesPracticed = if (form.attempted) form.timesPracticed.toIntOrNull() ?: 0 else 0,
            proficiency = if (form.attempted) form.proficiency.toIntOrNull() else null
        )

        repo.updateMove(id,move)
        return true
    }

}