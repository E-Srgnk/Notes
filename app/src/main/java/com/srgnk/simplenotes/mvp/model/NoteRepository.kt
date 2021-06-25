package com.srgnk.simplenotes.mvp.model

import javax.inject.Inject

class NoteRepository @Inject constructor(private val db: NoteDatabase) : DatabaseRepository {

    override suspend fun insert(note: Note): Long = db.noteDao().insert(note)

    override suspend fun delete(note: Note) = db.noteDao().delete(note)

    override suspend fun getAllNotes(): MutableList<Note> = db.noteDao().getAllNotes()

    override suspend fun getNoteById(noteId: Long): Note = db.noteDao().getNoteById(noteId)
}