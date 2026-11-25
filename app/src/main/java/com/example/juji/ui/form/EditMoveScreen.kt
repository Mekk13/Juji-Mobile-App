package com.example.juji.ui.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditMoveScreen(onSaved: ()->Unit,onCancel: () -> Unit, viewModel: EditMoveViewModel = viewModel()) {
    LaunchedEffect(viewModel.saved) { if (viewModel.saved) onSaved() }

    MoveForm(
            form = viewModel.form,
            onFormChange = {viewModel.updateForm(it)},
            onLastAttemptPicked = {viewModel.updateLastAttempt(it)},
            title = "Edit move",
            saveText = "Update",
            onSave = { viewModel.saveMove() },
            onCancel = onCancel,
            saveError =  viewModel.saveError
        )
}