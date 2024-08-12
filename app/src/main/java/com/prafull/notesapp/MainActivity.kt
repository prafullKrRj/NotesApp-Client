package com.prafull.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prafull.notesapp.auth.ui.AuthScreen
import com.prafull.notesapp.auth.ui.LoginScreen
import com.prafull.notesapp.auth.ui.SignUpScreen
import com.prafull.notesapp.main.ui.screens.HomeScreen
import com.prafull.notesapp.main.ui.theme.NotesAppTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val mainNavController = rememberNavController()
                Scaffold(
                    Modifier
                        .imePadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = mainNavController,
                        startDestination = MajorRoutes.AuthScreen
                    ) {
                        homeNavigation(mainNavController)
                        authNavigation(mainNavController)
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    navigation<MajorRoutes.HomeScreen>(startDestination = HomeRoutes.HomeScreen) {
        composable<HomeRoutes.HomeScreen> {
            HomeScreen(navController)
        }
    }
}

fun NavGraphBuilder.authNavigation(navController: NavController) {
    navigation<MajorRoutes.AuthScreen>(startDestination = AuthRoutes.MainScreen) {
        composable<AuthRoutes.LoginScreen> {
            LoginScreen(navController, getViewModel())
        }
        composable<AuthRoutes.RegisterScreen> {
            SignUpScreen(navController)
        }
        composable<AuthRoutes.MainScreen> {
            AuthScreen(navController)
        }
    }
}