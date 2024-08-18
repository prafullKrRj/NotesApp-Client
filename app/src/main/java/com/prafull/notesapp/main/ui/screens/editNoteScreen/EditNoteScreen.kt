package com.prafull.notesapp.main.ui.screens.editNoteScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.prafull.notesapp.R
import com.prafull.notesapp.goBackStack
import com.prafull.notesapp.main.data.UpdateNoteItem
import com.prafull.notesapp.main.ui.screens.createNote.EditableButton
import com.prafull.notesapp.main.ui.screens.createNote.StyleType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: EditNoteViewModel, navController: NavController) {
    val titleState = rememberRichTextState()
    val contentState = rememberRichTextState()
    LaunchedEffect(Unit) {
        titleState.setMarkdown(viewModel.getRealNote().title)
        contentState.setMarkdown(viewModel.getRealNote().content)
    }
    var selectedStyles by rememberSaveable { mutableStateOf(emptySet<StyleType>()) }
    var isEditing by rememberSaveable {
        mutableStateOf(true)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {

            }, actions = {
                IconButton(onClick = {
                    viewModel.note = UpdateNoteItem(
                        title = titleState.toMarkdown(),
                        content = contentState.toMarkdown(),
                        id = viewModel.getRealNote()._id
                    )
                    viewModel.updateNote()
                    navController.goBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_save_24),
                        contentDescription = "Save"
                    )
                }
            }, navigationIcon = {
                IconButton(onClick = {
                    viewModel.setFinalNote()
                    titleState.setMarkdown(viewModel.note.title)
                    contentState.setMarkdown(viewModel.note.content)
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            })
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxSize(),
            contentPadding = paddingValues
        ) {
            item {
                OutlinedRichTextEditor(
                    state = titleState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = RichTextEditorDefaults.outlinedRichTextEditorColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    placeholder = { Text("Title...") },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(StyleType.entries.toTypedArray()) { style ->
                        EditableButton(
                            style = style, onClick = {
                                selectedStyles = selectedStyles.toMutableSet().apply {
                                    if (contains(style)) remove(style) else add(style)
                                }
                                when (style.style) {
                                    is SpanStyle -> contentState.toggleSpanStyle(style.style)
                                    is ParagraphStyle -> contentState.toggleParagraphStyle(style.style)
                                }
                            }, selected = style in selectedStyles
                        )
                    }
                }
                OutlinedRichTextEditor(
                    state = contentState,
                    modifier = Modifier.fillMaxSize(),
                    colors = RichTextEditorDefaults.outlinedRichTextEditorColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    placeholder = { Text("Content...") }
                )
            }
        }
    }
}