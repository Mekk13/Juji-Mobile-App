package com.example.juji.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juji.ui.form.MoveForm
import com.example.juji.ui.list.MoveListScreen

@Composable
fun JujiNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MoveListRoute,
        modifier = modifier
    ){
        composable<MoveListRoute> {
            MoveListScreen(onAddClick = {navController.navigate(AddMoveRoute)})
        }
        composable<AddMoveRoute> {
            MoveForm()
        }
    }
}