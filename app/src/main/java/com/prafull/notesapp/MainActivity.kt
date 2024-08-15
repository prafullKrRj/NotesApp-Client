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
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.prafull.notesapp.auth.ui.AuthScreen
import com.prafull.notesapp.auth.ui.AuthViewModel
import com.prafull.notesapp.auth.ui.LoginScreen
import com.prafull.notesapp.auth.ui.SignUpScreen
import com.prafull.notesapp.main.ui.screens.createNote.CreateNoteScreen
import com.prafull.notesapp.main.ui.screens.editNoteScreen.EditNoteScreen
import com.prafull.notesapp.main.ui.screens.editNoteScreen.EditNoteViewModel
import com.prafull.notesapp.main.ui.screens.home.HomeScreen
import com.prafull.notesapp.main.ui.theme.NotesAppTheme
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val mainNavController = rememberNavController()
                val pref = getSharedPreferences("notes_pref", MODE_PRIVATE)
                Scaffold(
                    Modifier
                        .imePadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = mainNavController,
                        startDestination = if (pref.getBoolean(
                                "isLoggedIn",
                                false
                            )
                        ) MajorRoutes.HomeScreen else MajorRoutes.AuthScreen
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
            HomeScreen(navController, getViewModel())
        }
        composable<HomeRoutes.NewNoteScreen> {
            CreateNoteScreen(viewModel = getViewModel(), navController = navController)
        }
        composable<HomeRoutes.EditNoteScreen> {
            val data: HomeRoutes.EditNoteScreen = it.toRoute()
            val viewModel: EditNoteViewModel = getViewModel {
                parametersOf(data.toNoteItem())
            }
            EditNoteScreen(viewModel, navController)
        }
    }
}

fun NavGraphBuilder.authNavigation(navController: NavController) {
    navigation<MajorRoutes.AuthScreen>(startDestination = AuthRoutes.MainScreen) {
        composable<AuthRoutes.LoginScreen> {
            val viewModel: AuthViewModel = koinViewModel()
            LoginScreen(navController, viewModel)
        }
        composable<AuthRoutes.RegisterScreen> {
            val viewModel: AuthViewModel = koinViewModel()
            SignUpScreen(navController, viewModel)
        }
        composable<AuthRoutes.MainScreen> {
            AuthScreen(navController)
        }
    }
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}

fun NavController.clearCompleteBackStack() {
    while (currentBackStackEntry != null) {
        popBackStack()
    }
}