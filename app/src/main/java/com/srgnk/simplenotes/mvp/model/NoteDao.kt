package com.srgnk.simplenotes.mvp.model

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note): Long

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes(): MutableList<Note>
}