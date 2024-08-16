package com.prafull.notesapp.main.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.managers.markdownToPlainText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: NoteItem,
    selected: Boolean,
    onNoteToggled: () -> Unit,
    navController: NavController,
    isSelecting: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = {
                    if (isSelecting) {
                        onNoteToggled()
                    } else {
                        navController.navigate(note.toEditNoteRoute())
                    }
                },
                onLongClick = {
                    onNoteToggled()
                }
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(.8f)
            ) {
                Text(
                    text = markdownToPlainText(note.title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = markdownToPlainText(note.content),
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Created: ${note.createdAt}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Last Updated: ${note.updatedAt}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

            }
            if (isSelecting) {
                Checkbox(modifier = Modifier.weight(.2f), checked = selected, onCheckedChange = {
                    onNoteToggled()
                })
            }
        }
    }
}