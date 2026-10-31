package com.example.juji.ui.form

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juji.model.MoveFormState
import com.example.juji.ui.components.JujiButton
import com.example.juji.ui.components.JujiTextField
import com.example.juji.ui.theme.DarkGreen
import com.example.juji.ui.theme.DarkRed
import com.example.juji.ui.theme.LimeGreen
import com.example.juji.ui.theme.MossGreen
import com.example.juji.ui.theme.PaleGreen
import com.example.juji.ui.theme.Salmon


@Composable
fun ColumnEntry(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
) {
    Column(modifier = modifier) {
        Text(text, style = MaterialTheme.typography.labelLarge, fontSize = 20.sp)
        JujiTextField(
            value = value,
            onValueChange = onValueChange,
            error = error,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RowEntry(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    fieldModifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge,fontSize = 20.sp)
        JujiTextField(
            value = value,
            onValueChange = onValueChange,
            error = error,
            modifier = fieldModifier.weight(1f),
            keyboardOptions = keyboardOptions,
            readOnly = readOnly
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveForm(
    form: MoveFormState,
    onFormChange: (MoveFormState) -> Unit,
    onLastAttemptPicked: (Long?) -> Unit,
    title: String,
    saveText: String,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = PaleGreen
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    title, style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                ColumnEntry(
                    text = "Name:",
                    value = form.name,
                    onValueChange = { onFormChange(form.copy(name = it)) },
                    error = form.nameError
                )

                ColumnEntry(
                    text = "Category:",
                    value = form.category,
                    onValueChange = { onFormChange(form.copy(category = it)) },
                    error = form.categoryError
                )

                ColumnEntry(
                    text = "Starting position:",
                    value = form.startPos,
                    onValueChange = { onFormChange(form.copy(startPos = it)) },
                    error = form.startPosError
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text("Attempted?", style = MaterialTheme.typography.labelLarge)
                    JujiButton(
                        text = "Yes",
                        onClick = { onFormChange(form.copy(attempted = true)) },
                        containerColor = if (form.attempted) LimeGreen else Color.White,
                        borderColor = DarkGreen,
                        contentColor = DarkGreen,
                        height = 25.dp
                    )
                    JujiButton(
                        text = "No",
                        onClick = { onFormChange(form.copy(attempted = false)) },
                        containerColor = if (!form.attempted) LimeGreen else Color.White,
                        borderColor = DarkGreen,
                        contentColor = DarkGreen,
                        height = 25.dp
                    )
                }

                if (form.attempted) {
                    RowEntry(
                        text = "Last attempt:",
                        value = form.lastAttempt?.toString() ?: "",
                        onValueChange = { },
                        error = form.lastAttemptError,
                        readOnly = true,
                        fieldModifier = Modifier.pointerInput(Unit) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePicker = true
                                }
                            }
                        }
                    )
                    RowEntry(
                        text = "Times practiced:",
                        value = form.timesPracticed,
                        onValueChange = { onFormChange(form.copy(timesPracticed = it)) },
                        error = form.timesPracticedError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Row {
                        RowEntry(
                            text = "Proficiency:",
                            value = form.proficiency,
                            onValueChange = { onFormChange(form.copy(proficiency = it)) },
                            error = form.proficiencyError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "/10",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 21.sp,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    JujiButton(
                        onClick = { onCancel() },
                        text = "Cancel",
                        containerColor = Salmon,
                        contentColor = DarkRed,
                        borderColor = DarkRed
                    )
                    JujiButton(
                        onClick = { onSave() },
                        text = saveText,
                        containerColor = LimeGreen,
                        contentColor = DarkGreen,
                        borderColor = MossGreen
                    )
                }

            }
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