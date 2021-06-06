package com.srgnk.simplenotes.mvp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var title: String,
    var content: String,
    var date: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}