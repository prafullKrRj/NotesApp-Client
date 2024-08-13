package com.prafull.notesapp.main.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.notesapp.main.domain.models.NoteItem

@Composable
fun NoteCard(note: NoteItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate(note.toEditableNote())
            }
            .padding(16.dp)) {
            Text(
                text = note.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
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
    }
}