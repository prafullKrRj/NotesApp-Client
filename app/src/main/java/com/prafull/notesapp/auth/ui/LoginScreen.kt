package com.prafull.notesapp.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.notesapp.MajorRoutes
import com.prafull.notesapp.auth.ui.components.EmailAndPassword
import com.prafull.notesapp.clearCompleteBackStack

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    LaunchedEffect(viewModel.hasLoggedIn) {
        if (viewModel.hasLoggedIn) {
            navController.clearCompleteBackStack()
            navController.navigate(MajorRoutes.HomeScreen)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        EmailAndPassword(
            email = email,
            password = password,
            emailError = emailError,
            passwordError = passwordError,
            onEmailChange = {
                email = it
                emailError = false
            },
            onPasswordChange = {
                password = it
                passwordError = false
            }
        )
        Button(
            onClick = {
                emailError = email.isBlank()
                passwordError = password.isBlank()
                if (!emailError && !passwordError) {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Login")
        }
    }
}