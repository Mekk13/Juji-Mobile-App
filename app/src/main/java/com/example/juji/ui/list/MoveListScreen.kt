package com.example.juji.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juji.model.Move
import com.example.juji.ui.components.JujiButton
import com.example.juji.ui.theme.DarkGreen
import com.example.juji.ui.theme.DarkRed
import com.example.juji.ui.theme.GreyGreen
import com.example.juji.ui.theme.LimeGreen
import com.example.juji.ui.theme.MossGreen
import com.example.juji.ui.theme.Orange
import com.example.juji.ui.theme.PaleGreen
import com.example.juji.ui.theme.PaleYellow
import com.example.juji.ui.theme.Salmon

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
fun MoveCard(move: Move, onEditClick: () -> Unit, onDeleteClick: () -> Unit, modifier: Modifier = Modifier){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(move.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(move.category, style = MaterialTheme.typography.bodySmall)
            }


            Column(horizontalAlignment = Alignment.End) {
                val proficiency = move.proficiency
                if (proficiency !=null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text("${proficiency}/10", fontSize = 13.sp)
                        Box(
                            modifier = Modifier
                                .offset(y = (-1).dp)
                                .width(140.dp)
                                .height(12.dp)
                                .background(PaleYellow, RoundedCornerShape(50))
                                .padding(2.dp)
                        ){
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(proficiency / 10f)
                                    .background(Orange, RoundedCornerShape(50))
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    JujiButton(
                        onClick = {onEditClick()},
                        text = "Edit",
                        containerColor = LimeGreen,
                        contentColor = DarkGreen,
                        borderColor = MossGreen)
                    JujiButton(
                        onClick = {onDeleteClick()},
                        text = "Delete",
                        containerColor = Salmon,
                        contentColor = DarkRed,
                        borderColor = DarkRed)
                }
            }
        }
    }
}

@Composable
fun KindOfSearchBar( query: String, onSearchChange: (String) -> Unit, modifier : Modifier = Modifier){

    BasicTextField(
        value = query,
        onValueChange = onSearchChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = DarkGreen),
        modifier = modifier
            .height(40.dp)
            .shadow(4.dp, RoundedCornerShape(50))
            .background(PaleGreen, RoundedCornerShape(50))
            .padding(horizontal = 16.dp),
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text("search...", color = GreyGreen, style = MaterialTheme.typography.labelLarge)
                    }
                    innerTextField()
                }
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = GreyGreen)
            }
        }
    )
}

@Composable
fun MoveListScreen(viewModel: MoveListViewModel = viewModel(),
                   onAddClick: () -> Unit,
                   onEditClick: (Int) -> Unit,
                   modifier: Modifier = Modifier){

    val moves by viewModel.moves.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val selectedMove = remember { mutableStateOf<Move?>(null) }

    Box (
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(53.dp))
            Text("Move log", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))


            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)){
                KindOfSearchBar(query,{viewModel.updateQuery(it)},modifier = Modifier.weight(1f))
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = PaleGreen
                    ),
                    modifier = Modifier.height(40.dp)
                )
                {
                    Text("Filter", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PaleGreen
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(moves, key = { it.id }) { move ->
                        MoveCard(
                            move = move,
                            onEditClick = { onEditClick(move.id) },
                            onDeleteClick = { selectedMove.value = move }
                        )
                    }

                }
            }
        }

        FloatingActionButton(
            onClick = {onAddClick()},
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = PaleGreen,
            contentColor = DarkGreen
        ) {
            Icon(Icons.Filled.Add, "Floating add button")
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