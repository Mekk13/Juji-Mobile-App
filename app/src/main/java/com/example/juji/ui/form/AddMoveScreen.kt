package com.example.juji.ui.form


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AddMoveScreen(onSaved: ()->Unit, viewModel: AddMoveViewModel = viewModel()) {

    Column {
        Text("Add move", style = MaterialTheme.typography.headlineMedium)

        MoveForm(
            form = viewModel.form,
            onFormChange = {viewModel.updateForm(it)},
            onLastAttemptPicked = {viewModel.updateLastAttempt(it)}
        )

        Button(
            onClick = {
                if (viewModel.saveMove()) {
                    onSaved()
                }
            }
        ) {
            Text("Save")
        }
    }


}