package com.example.juji.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoveListScreen(viewModel: MoveListViewModel = viewModel(), modifier: Modifier){
    val moves by viewModel.moves.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier) {
        items(moves, key = {it.id}){ move ->
            Text(move.name)
        }
    }
}