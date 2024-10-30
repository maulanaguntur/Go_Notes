package com.lamz.go_notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lamz.go_notes.domain.Notes
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Insert
    suspend fun insert(notes: Notes)

    @Query("SELECT * FROM go_notes")
    fun getAllNotes() : Flow<List<Notes>>

    @Delete
    suspend fun delete(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Query("SELECT * FROM go_notes WHERE title LIKE '%' || :query || '%' OR desc LIKE '%' || :query || '%' ")
    fun searchNotes(query : String) : Flow<List<Notes>>

}