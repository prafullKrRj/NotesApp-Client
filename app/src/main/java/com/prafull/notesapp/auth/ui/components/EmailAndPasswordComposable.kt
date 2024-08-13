package com.prafull.notesapp.auth.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.prafull.notesapp.R

@Composable
fun EmailAndPassword(
    email: String,
    password: String,
    emailError: Boolean,
    passwordError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = { Text("Email") },
        isError = emailError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    )
    if (emailError) {
        Text("Email is required", color = MaterialTheme.colorScheme.error)
    }
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = { Text("Password") },
        isError = passwordError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        trailingIcon = {
            IconButton(onClick = {
                showPassword = !showPassword
            }) {
                Icon(
                    painter = painterResource(id = if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                    contentDescription = null
                )
            }
        },
    )
    if (passwordError) {
        Text("Password is required", color = MaterialTheme.colorScheme.error)
    }

    Spacer(modifier = Modifier.height(8.dp))
}