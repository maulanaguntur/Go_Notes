package com.lamz.go_notes.data.repository

import android.app.Application
import com.lamz.go_notes.data.local.NotesDao
import com.lamz.go_notes.data.local.NotesDatabase
import com.lamz.go_notes.domain.Notes
import kotlinx.coroutines.flow.Flow

class NotesRepository(application: Application) {
    private val mNotesDao : NotesDao

    init {
        val db = NotesDatabase.getDatabase(application)
        mNotesDao = db.notesDao()
    }

    fun getAllNotes(): Flow<List<Notes>> = mNotesDao.getAllNotes()

    suspend fun insert(notes : Notes) = mNotesDao.insert(notes)

    suspend fun delete(notes: Notes) = mNotesDao.delete(notes)

    suspend fun update(notes: Notes) = mNotesDao.update(notes)

    fun searchNotes(searchQuery : String ) : Flow<List<Notes>> = mNotesDao.searchNotes(searchQuery)
}