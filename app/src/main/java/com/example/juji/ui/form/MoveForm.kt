package com.example.juji.ui.form

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.juji.model.MoveFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveForm(
    form: MoveFormState,
    onFormChange: (MoveFormState) -> Unit,
    onLastAttemptPicked: (Long?) ->Unit
) {

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    
    Column{
        OutlinedTextField(
            value = form.name,
            label ={Text("name")},
            onValueChange = { newText ->
                onFormChange(form.copy(name = newText))
            },
            isError = form.nameError != null,
            supportingText = { form.nameError?.let { Text(it) } }
        )
        OutlinedTextField(
            value = form.category,
            label = { Text("category")},
            onValueChange = { onFormChange(form.copy(category = it))},
            isError = form.categoryError != null,
            supportingText = { form.categoryError?.let { Text(it) } }
        )
        OutlinedTextField(
            value = form.startPos,
            label = { Text("Starting Position")},
            onValueChange = {onFormChange(form.copy(startPos = it))}
        )

        Row {
            Text("Attempted?")
            Checkbox(
                checked = form.attempted,
                onCheckedChange ={onFormChange(form.copy(attempted = it))}
            )
        }
        if(form.attempted){
            OutlinedTextField(
                value = form.timesPracticed,
                label = { Text("Times practiced")},
                onValueChange = {onFormChange(form.copy(timesPracticed = it))},
                isError = form.timesPracticedError != null,
                supportingText = { form.timesPracticedError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = form.proficiency,
                label = { Text("Proficiency (1-10)")},
                onValueChange = {onFormChange(form.copy(proficiency = it))},
                isError = form.proficiencyError != null,
                supportingText = { form.proficiencyError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = form.lastAttempt?.toString() ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text("Last attempt") },
                isError = form.lastAttemptError != null,
                supportingText = { form.lastAttemptError?.let { Text(it) } },
                modifier = Modifier.pointerInput(Unit){
                    awaitEachGesture{
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if(upEvent != null){
                            showDatePicker = true
                        }
                    }
                }
            )
        }
    }

    if (showDatePicker) {

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    onLastAttemptPicked(millis)
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }){
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}