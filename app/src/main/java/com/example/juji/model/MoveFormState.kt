package com.example.juji.model

import java.time.LocalDate

data class MoveFormState(
    val name: String = "",
    val category: String = "",
    val startPos: String = "",
    val attempted: Boolean = false,
    val timesPracticed: String = "",
    val proficiency: String = "",
    val lastAttempt: LocalDate? = null,

    val nameError: String? = null,
    val categoryError: String? = null,
    val startPosError: String? = null,
    val attemptedError: String? = null,
    val timesPracticedError: String? = null,
    val proficiencyError: String? = null,
    val lastAttemptError: String? = null,
)