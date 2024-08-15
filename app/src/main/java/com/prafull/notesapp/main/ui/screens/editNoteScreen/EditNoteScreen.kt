package com.prafull.notesapp.main.ui.screens.editNoteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import com.prafull.notesapp.main.ui.screens.createNote.EditableButton
import com.prafull.notesapp.main.ui.screens.createNote.StyleType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: EditNoteViewModel, navController: NavController) {
    var isEditing by rememberSaveable {
        mutableStateOf(false)
    }
    val titleState = rememberRichTextState()

    val contentState = rememberRichTextState()

    LaunchedEffect(Unit) {
        titleState.setMarkdown(viewModel.editableNote.title)
        contentState.setMarkdown(viewModel.editableNote.content)
    }
    var selectedStyles by remember { mutableStateOf(setOf<StyleType>()) }

    LaunchedEffect(titleState.toMarkdown().length, contentState.toMarkdown().length) {

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (titleState.toMarkdown().isNotEmpty() || contentState.toMarkdown().isNotEmpty()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = {
                    // TODO: Update note
                    navController.popBackStack()
                }) {
                    Text(text = "Save")
                }
            }
        }
        RichText(
            state = titleState,
            modifier = Modifier.fillMaxWidth()
        )
        if (isEditing) {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(StyleType.entries.toTypedArray()) { style ->
                    EditableButton(
                        style = style,
                        onClick = {
                            selectedStyles = selectedStyles.toMutableSet().apply {
                                if (contains(style)) remove(style) else add(style)
                            }
                            when (style.style) {
                                is SpanStyle -> contentState.toggleSpanStyle(style.style)
                                is ParagraphStyle -> contentState.toggleParagraphStyle(style.style)
                            }
                        },
                        selected = style in selectedStyles
                    )
                }
            }
        }
        RichText(
            state = contentState,
            modifier = Modifier.fillMaxSize()
        )
    }
}