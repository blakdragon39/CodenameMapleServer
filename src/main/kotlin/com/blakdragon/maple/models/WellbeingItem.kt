package com.blakdragon.maple.models

import com.blakdragon.maple.interfaces.PetConsumable

class WellbeingItem (
    id: String,
    displayName: String,
    description: String,
    val wellbeingStat: WellbeingStat,
    val effect: Int
) : Item(id = id, description = description, displayName = displayName), PetConsumable {

    override fun consume(user: User, pet: Pet) {
        when (wellbeingStat) {
            WellbeingStat.Hunger -> pet.wellbeing.hunger += effect
            WellbeingStat.Hygiene -> pet.wellbeing.hygiene += effect
            WellbeingStat.Mood -> pet.wellbeing.mood += effect
        }

        capStats(pet)
    }

    private fun capStats(pet: Pet) {
        if (pet.wellbeing.hunger > WELLBEING_CAP) {
            pet.wellbeing.hunger = WELLBEING_CAP
        }

        if (pet.wellbeing.hygiene > WELLBEING_CAP) {
            pet.wellbeing.hygiene = WELLBEING_CAP
        }

        if (pet.wellbeing.mood > WELLBEING_CAP) {
            pet.wellbeing.mood = WELLBEING_CAP
        }
    }
}
