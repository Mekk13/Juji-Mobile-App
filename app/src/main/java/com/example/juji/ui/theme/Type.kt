package com.example.juji.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.juji.R

val Bitcount = FontFamily(
    Font(R.font.bitcount_double_regular)
)

val CourierPrime = FontFamily(
    Font(R.font.courier_prime_regular, FontWeight.Normal),
    Font(R.font.courier_prime_bold, FontWeight.Bold)
)

private val default = Typography()

val Typography = Typography(
    headlineLarge = default.headlineLarge.copy(fontFamily = Bitcount),
    headlineMedium = default.headlineMedium.copy(fontFamily = Bitcount),
    headlineSmall = default.headlineMedium.copy(fontFamily = Bitcount),
    titleLarge = default.titleLarge.copy(fontFamily = Bitcount),
    titleMedium = default.titleMedium.copy(fontFamily = Bitcount),
    labelLarge = default.labelLarge.copy(fontFamily = Bitcount),
    labelMedium = default.labelMedium.copy(fontFamily = Bitcount),
    bodyLarge = default.bodyLarge.copy(fontFamily = CourierPrime),
    bodyMedium = default.bodyMedium.copy(fontFamily = CourierPrime),
    bodySmall = default.bodySmall.copy(fontFamily = CourierPrime)
)
