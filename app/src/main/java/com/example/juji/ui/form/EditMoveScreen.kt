package com.example.juji.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditMoveScreen(onSaved: ()->Unit,onCancel: () -> Unit, viewModel: EditMoveViewModel = viewModel()) {

    MoveForm(
            form = viewModel.form,
            onFormChange = {viewModel.updateForm(it)},
            onLastAttemptPicked = {viewModel.updateLastAttempt(it)},
            title = "Edit move",
            saveText = "Update",
            onSave = { if (viewModel.saveMove()) onSaved() },
            onCancel = onCancel,
        )
}