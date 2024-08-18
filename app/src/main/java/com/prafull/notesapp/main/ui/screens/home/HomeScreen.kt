package com.prafull.notesapp.main.ui.screens.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.notesapp.HomeRoutes
import com.prafull.notesapp.managers.BaseClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val selectedNotes by viewModel.selectedNotes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllNotes()
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(HomeRoutes.NewNoteScreen)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }, topBar = {
        if (selectedNotes.isNotEmpty()) {
            CenterAlignedTopAppBar(title = {
                Text(text = "${selectedNotes.size} selected")
            }, actions = {
                IconButton(onClick = {
                    viewModel.deleteSelectedNotes()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete selected notes"
                    )
                }
            }, navigationIcon = {
                IconButton(onClick = {
                    viewModel.clearSelectedNotes()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = "Close selection"
                    )
                }
            })
        } else {
            CenterAlignedTopAppBar(title = {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    SearchBar(
                        modifier = Modifier.weight(.85f),
                        viewModel = viewModel,
                        searchQuery = searchQuery
                    ) {
                        searchQuery = it
                    }
                    IconButton(onClick = {
                        navController.navigate(HomeRoutes.ProfileScreen)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "navigate to profile",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            })
        }
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            when (val currentState = state) {
                is BaseClass.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is BaseClass.Success -> {
                    if (currentState.data.isEmpty()) {
                        NoNotesFoundMessage()
                    } else {
                        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(12.dp)) {
                            items(currentState.data) {
                                NoteCard(
                                    note = it,
                                    selected = selectedNotes.contains(it),
                                    onNoteToggled = {
                                        viewModel.toggleNote(it)
                                    },
                                    navController = navController,
                                    isSelecting = selectedNotes.isNotEmpty()
                                )
                            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    modifier: Modifier,
    viewModel: HomeViewModel,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            onSearchQueryChange(it)
            viewModel.filterNotes(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp),
        placeholder = { Text("Search...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onSearchQueryChange("")
                    viewModel.filterNotes("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
            cursorColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun ErrorCom(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "An error occurred!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun NoNotesFoundMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sorry, no notes found!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Try adding some notes to get started.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}