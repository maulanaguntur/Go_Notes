package com.lamz.go_notes.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "go_notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val date : String,
    val title : String,
    val desc : String,
) : Parcelable
