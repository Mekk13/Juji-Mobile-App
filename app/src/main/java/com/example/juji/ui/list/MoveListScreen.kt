package com.example.juji.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.juji.model.Move

@Composable
fun ConfirmDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
)
{
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Warning Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text ={
            Text(text = dialogText)
        },
        onDismissRequest ={
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ){
                Text("Cancel")
            }
        }
    )
}
@Composable
fun MoveListScreen(viewModel: MoveListViewModel = viewModel(), modifier: Modifier){
    val moves by viewModel.moves.collectAsStateWithLifecycle()

    val openDeleteDialog = remember { mutableStateOf(false) }
    val selectedMove = remember { mutableStateOf<Move?>(null) }

    LazyColumn(modifier = modifier) {
        items(moves, key = {it.id}){ move ->
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(move.name)
                Button(onClick = {
                    openDeleteDialog.value = true
                    selectedMove.value = move
                }){
                    Text("Delete")
                }
            }
        }
    }

    val moveToDelete = selectedMove.value
    if (moveToDelete != null){
        ConfirmDeleteDialog(
            onDismissRequest = { selectedMove.value = null},
            onConfirmation = {
                viewModel.deleteMove(moveToDelete.id)
                selectedMove.value = null
            },
            dialogTitle = "Confirm Action",
            dialogText = "Are you ABSOLUTELY CERTAIN that you wish to delete this move???? Forever??",
            icon = Icons.Default.Warning
        )
    }

}