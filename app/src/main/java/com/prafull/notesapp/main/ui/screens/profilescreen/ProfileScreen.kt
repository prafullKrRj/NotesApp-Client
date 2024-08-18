package com.prafull.notesapp.main.ui.screens.profilescreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.notesapp.MajorRoutes
import com.prafull.notesapp.clearCompleteBackStack
import com.prafull.notesapp.goBackStack
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val loading by viewModel.loading.collectAsState()
    BackHandler {
        if (!loading) {
            navController.goBackStack()
        }
    }
    var showDeletionDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Profile")
        }, navigationIcon = {
            IconButton(onClick = {
                navController.goBackStack()
            }, enabled = !loading) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) { paddingValues ->
        if (showDeletionDialog) {
            AlertDialog(onDismissRequest = {
                if (!loading) {
                    showDeletionDialog = false
                }
            }, confirmButton = {
                if (!loading) {
                    TextButton(onClick = {
                        viewModel.deleteAccount()
                        navController.clearCompleteBackStack()
                        navController.navigate(MajorRoutes.AuthScreen)
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }, title = {
                if (!loading) Text(text = "Are you sure you want to delete your account?")
            }, text = {
                if (loading) {
                    CircularProgressIndicator()
                }
            }, dismissButton = {
                if (!loading) {
                    TextButton(onClick = {
                        showDeletionDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            })
        }
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically)
                ) {
                    if (!viewModel.user.name.isNullOrBlank()) {
                        Text(
                            text = "Name: ${viewModel.user.name!!.capitalize(Locale.getDefault())}"
                        )
                    }
                    Text(text = "Email: ${viewModel.user.email}")
                }
            }
            FilledTonalButton(onClick = {
                navController.clearCompleteBackStack()
                viewModel.clearPrefs()
                navController.navigate(MajorRoutes.AuthScreen)
            }, enabled = !loading) {
                Text(text = "Sign Out")
            }
            FilledTonalButton(onClick = {
                showDeletionDialog = true
            }, enabled = !loading) {
                Text(text = "Delete Account")
            }
        }
    }
}