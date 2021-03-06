package com.srgnk.simplenotes.mvp.model

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    suspend fun getAllNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): Note
}