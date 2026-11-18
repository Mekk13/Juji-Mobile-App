package com.example.juji.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.time.LocalDate

@Entity
data class Move(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,

    val name: String,
    val category: String,
    val startingPosition: String? = null,
    val attempted: Boolean = false,
    val lastAttempt: LocalDate? = null,
    val timesPracticed: Int = 0,
    val proficiency: Int? = null
)
