package com.blakdragon.maple.models

data class Wellbeing(
    var hunger: Int = 0,
    var hygiene: Int = 0,
    var mood: Int = 0
)

enum class WellbeingStat {
    Hunger,
    Hygiene,
    Mood
}
