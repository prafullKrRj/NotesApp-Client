package com.prafull.notesapp.main.ui.screens.createNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.prafull.notesapp.R
import com.prafull.notesapp.goBackStack
import com.prafull.notesapp.main.domain.models.CreateNoteItem

enum class StyleType(val icon: Int, val style: Any) {
    BOLD(R.drawable.baseline_format_bold_24, SpanStyle(fontWeight = FontWeight.Bold)),
    ITALIC(R.drawable.baseline_format_italic_24, SpanStyle(fontStyle = FontStyle.Italic)),
    UNDERLINE(
        R.drawable.baseline_format_underlined_24,
        SpanStyle(textDecoration = TextDecoration.Underline)
    ),
    TITLE(
        R.drawable.baseline_title_24,
        SpanStyle(fontSize = 36.sp)
    ),
    SUBTITLE(
        R.drawable.baseline_format_size_24,
        SpanStyle(fontSize = 22.sp)
    ),
    COLOR(R.drawable.baseline_format_color_text_24, SpanStyle(color = Color.Red)),
    ALIGN_LEFT(
        R.drawable.baseline_format_align_left_24,
        ParagraphStyle(textAlign = TextAlign.Left)
    ),
    ALIGN_CENTER(
        R.drawable.baseline_format_align_center_24,
        ParagraphStyle(textAlign = TextAlign.Center)
    ),
    ALIGN_RIGHT(
        R.drawable.baseline_format_align_right_24,
        ParagraphStyle(textAlign = TextAlign.Right)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(viewModel: CreateNoteVM, navController: NavController) {
    val titleState = rememberRichTextState()
    val contentState = rememberRichTextState()
    var selectedStyles by remember { mutableStateOf(setOf<StyleType>()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("Create Note")
            }, actions = {
                IconButton(onClick = {
                    viewModel.note =
                        CreateNoteItem(titleState.toMarkdown(), contentState.toMarkdown())
                    viewModel.saveNote()
                    navController.goBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_save_24),
                        contentDescription = "Save"
                    )
                }
            })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

@Composable
fun EditableButton(
    style: StyleType,
    onClick: () -> Unit,
    selected: Boolean
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.small
            )
    ) {
        Icon(
            painter = painterResource(id = style.icon),
            contentDescription = style.name,
            tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}