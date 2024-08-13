package com.prafull.notesapp.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.notesapp.HomeRoutes
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.managers.BaseClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(HomeRoutes.AddNoteScreen)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            CenterAlignedTopAppBar(title = {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.filterNotes(it)
                    }
                )
            })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            when (state) {
                is BaseClass.Loading -> {
                    CircularProgressIndicator()
                }

                is BaseClass.Success -> {
                    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(12.dp)) {
                        items((state as BaseClass.Success<List<NoteItem>>).data) {
                            NoteCard(note = it, navController)
                        }
                    }
                }

                is BaseClass.Error -> {
                    ErrorCom {
                        viewModel.getAllNotes()
                    }
                }
            }
        }

    }
}

@Composable
fun ErrorCom(onRetry: () -> Unit) {
    Button(onClick = onRetry) {
        Text(text = "Retry")
    }
}