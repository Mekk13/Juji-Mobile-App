package com.example.juji.ui.form


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AddMoveScreen(onSaved: ()->Unit,onCancel: () -> Unit, viewModel: AddMoveViewModel = viewModel()) {
    MoveForm(
            form = viewModel.form,
            onFormChange = {viewModel.updateForm(it)},
            onLastAttemptPicked = {viewModel.updateLastAttempt(it)},
            title = "Add move",
            saveText = "Save",
            onSave = { if (viewModel.saveMove()) onSaved() },
            onCancel = onCancel,)
}