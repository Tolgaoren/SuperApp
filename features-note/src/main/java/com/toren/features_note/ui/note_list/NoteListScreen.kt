package com.toren.features_note.ui.note_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.toren.domain.model.note.Note
import com.toren.features_note.ui.graphs.NoteScreens


@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedNotes by viewModel.selectedNotes
    val isItemsSelectable by viewModel.isItemsSelectable

    LaunchedEffect(Unit) {
        viewModel.onEvent(NoteListUiEvent.Refresh)
    }

    Surface {

        Column {
            Text(
                text = "Notes",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,

                modifier = Modifier
                    .padding(
                        start = 30.dp,
                        top = 10.dp,
                        bottom = 5.dp
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = uiState.error.toString())
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(10.dp),
                        content = {
                            items(uiState.notes) { note ->
                                NoteItem(
                                    note = note,
                                    isSelected = selectedNotes.contains(note),
                                    isSelectionModeOn = isItemsSelectable,
                                    onSelect = {
                                        viewModel.onEvent(NoteListUiEvent.OnNoteSelected(note))
                                    },
                                    onClick = {
                                        navController.navigate(
                                            NoteScreens.NoteDetailScreen.route + "/${note.id}",

                                            )
                                    },
                                    onLongPress = {
                                        viewModel.onEvent(NoteListUiEvent.OnNoteSelected(note))
                                        viewModel.onEvent(NoteListUiEvent.SelectionModeChanged)
                                    },
                                )
                            }
                        }
                    )
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(NoteScreens.CreateNoteScreen.route)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "New reminder action button."
                    )
                }

                if (isItemsSelectable) {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(NoteListUiEvent.OnNotesDeleted)
                            viewModel.onEvent(NoteListUiEvent.SelectionModeChanged)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete selected notes"
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(NoteListUiEvent.SelectionModeChanged)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "Cancel selection"
                        )
                    }


                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: Note,
    isSelected: Boolean,
    isSelectionModeOn: Boolean,
    onSelect: () -> Unit,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 5.dp,
                    bottom = 5.dp,
                    top = 5.dp
                )
                .height(120.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        if (isSelectionModeOn) {
                            onSelect()
                        } else {
                            onClick()
                        }
                    },
                    onLongClick = onLongPress
                ),
            shape = CardDefaults.elevatedShape,
            elevation = CardDefaults.cardElevation(5.dp)
        ) {

            if (isSelectionModeOn) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelect() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            Column {
                if (note.title.isNotBlank()) {
                    Text(
                        text = note.title,
                        style = TextStyle(
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(
                            top = 5.dp,
                            start = 5.dp,
                            end = 5.dp
                        )
                    )
                }
                if (note.content.isNotBlank()) {
                    Text(
                        text = note.content,
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(
                            top = 5.dp,
                            start = 5.dp,
                            end = 5.dp,
                            bottom = 5.dp
                        )
                    )
                }
            }
        }
        Text(
            text = note.timestamp,
            style = TextStyle(
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(
                    top = 5.dp,
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
                .fillMaxWidth()
        )
    }
}
