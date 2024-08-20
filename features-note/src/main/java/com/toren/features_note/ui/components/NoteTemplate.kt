package com.toren.features_note.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NoteTemplate(
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                TextField(
                    value = title,
                    onValueChange = { onTitleChange(it) },
                    textStyle = TextStyle(
                        fontSize = 25.sp
                    ),
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )

                TextField(
                    value = content,
                    onValueChange = { onContentChange(it) },
                    textStyle = TextStyle(
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start
                    ),
                    placeholder = {
                        Text(
                            text = "Content",
                            fontSize = 17.sp,
                            textAlign = TextAlign.Start
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )
            }

            FloatingActionButton(
                onClick = {
                    onSave()
                },
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.BottomEnd),
            )
            {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save button"
                )
            }
        }
    }
}