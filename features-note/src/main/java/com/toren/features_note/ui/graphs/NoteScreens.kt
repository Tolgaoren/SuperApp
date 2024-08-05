package com.toren.features_note.ui.graphs

sealed class NoteScreens(
    val route: String,
    val title: String,
) {
    object CreateNoteScreen : NoteScreens(
        route = "CreateNoteScreen",
        title = "CreateNoteScreen"
    )

    object NoteDetailScreen : NoteScreens(
        route = "NoteDetailScreen",
        title = "NoteDetailScreen"
    )
}