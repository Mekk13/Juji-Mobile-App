package com.example.juji.ui.form


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AddMoveScreen(onSaved: ()->Unit,onCancel: () -> Unit, viewModel: AddMoveViewModel = viewModel()) {
    LaunchedEffect(viewModel.saved) { if (viewModel.saved) onSaved() }
    MoveForm(
            form = viewModel.form,
            onFormChange = {viewModel.updateForm(it)},
            onLastAttemptPicked = {viewModel.updateLastAttempt(it)},
            title = "Add move",
            saveText = "Save",
            onSave = { viewModel.saveMove() },
            onCancel = onCancel,
            saveError =  viewModel.saveError
            )
}