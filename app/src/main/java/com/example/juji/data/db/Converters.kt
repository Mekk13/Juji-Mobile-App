package com.example.juji.data.db

import androidx.room3.ColumnTypeConverter
import java.time.LocalDate

object Converters {
    @ColumnTypeConverter
    fun fromLocalDateToString(value: LocalDate?): String?{
        return value?.toString()
    }

    @ColumnTypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate?{
        return value?.let{
            LocalDate.parse(it)
        }
    }
}