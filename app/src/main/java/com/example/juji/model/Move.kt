package com.example.juji.model

import java.time.LocalDate

data class Move(val id: Int, val name: String, val category: String, val startingPosition: String? = null, val attempted: Boolean = false, val lastAttempt: LocalDate? = null, val timesPracticed: Int = 0, val proficiency: Int? = null)
