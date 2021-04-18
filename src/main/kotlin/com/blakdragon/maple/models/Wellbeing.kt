package com.blakdragon.maple.models

data class Wellbeing(
    val hunger: Int = 0,
    val hygiene: Int = 0,
    val mood: Int = 0
)

enum class WellbeingStat {
    Hunger,
    Hygiene,
    Mood
}
