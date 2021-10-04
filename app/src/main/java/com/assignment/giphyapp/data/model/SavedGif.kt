package com.assignment.giphyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedGif(
    @PrimaryKey val userId:String,
    val gifImage:String
)
