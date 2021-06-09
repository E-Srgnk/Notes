package com.srgnk.simplenotes.mvp.model

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: Long): Note
}