package com.example.juji.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juji.ui.form.AddMoveScreen
import com.example.juji.ui.form.EditMoveScreen
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
            MoveListScreen(
                onAddClick = {navController.navigate(AddMoveRoute)},
                onEditClick = { id ->
                    navController.navigate(EditMoveRoute(id))
                })
        }

        composable<AddMoveRoute> {
            AddMoveScreen({navController.popBackStack()},onCancel = { navController.popBackStack() })
        }

        composable<EditMoveRoute>{
            EditMoveScreen(onSaved = {navController.popBackStack()}, onCancel = {navController.popBackStack()})
        }
    }
}