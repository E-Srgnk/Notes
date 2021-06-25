package com.srgnk.simplenotes.mvp.model

interface DatabaseRepository {

    suspend fun insert(note: Note): Long

    suspend fun delete(note: Note)

    suspend fun getAllNotes(): MutableList<Note>

    suspend fun getNoteById(noteId: Long): Note
}