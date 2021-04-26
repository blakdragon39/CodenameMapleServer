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
        //todo cap on each stat
        when (wellbeingStat) {
            WellbeingStat.Hunger -> pet.wellbeing.hunger += effect
            WellbeingStat.Hygiene -> pet.wellbeing.hygiene += effect
            WellbeingStat.Mood -> pet.wellbeing.mood += effect
        }
    }
}
