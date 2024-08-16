package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.data.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity) : Long

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int) : Int

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity
}