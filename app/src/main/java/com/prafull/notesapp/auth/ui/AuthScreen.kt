package com.prafull.notesapp.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.notesapp.AuthRoutes

@Composable
fun AuthScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var buttonClicked by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {
            buttonClicked = true
            navController.navigate(AuthRoutes.LoginScreen)
        }, enabled = !buttonClicked) {
            Text("Login")
        }
        FilledTonalButton(onClick = {
            buttonClicked = true
            navController.navigate(AuthRoutes.RegisterScreen)
        }, enabled = !buttonClicked) {
            Text("Sign Up")
        }
    }
}