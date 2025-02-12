package com.route.todoapp.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    var title: String? = null,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var date: Long? = null,
    @ColumnInfo
    var isDone: Boolean? = false
) : Parcelable
