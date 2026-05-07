package com.comind.myefreicard.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Card : BottomNavItem("card", "Card", Icons.Filled.CreditCard, Icons.Outlined.CreditCard)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
    object Schedule : BottomNavItem("schedule", "Schedule", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth)
    object Access : BottomNavItem("access", "Access", Icons.Filled.Security, Icons.Outlined.Security)
}

val bottomNavItems = listOf(
    BottomNavItem.Card,
    BottomNavItem.Profile,
    BottomNavItem.Schedule,
    BottomNavItem.Access
)
