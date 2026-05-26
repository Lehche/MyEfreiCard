package com.comind.myefreicard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.comind.myefreicard.ui.screens.AccessScreen
import com.comind.myefreicard.ui.screens.CardScreen
import com.comind.myefreicard.ui.screens.ProfileScreen
import com.comind.myefreicard.ui.screens.ScheduleScreen

@Composable
fun NavGraph(navController: NavHostController, onLogoutTriggered: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Card.route
    ) {
        composable(BottomNavItem.Card.route) {
            CardScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(onLogoutTriggered = onLogoutTriggered)
        }
        composable(BottomNavItem.Schedule.route) {
            ScheduleScreen()
        }
        composable(BottomNavItem.Access.route) {
            AccessScreen()
        }
    }
}
