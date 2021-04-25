package com.blakdragon.maple.interfaces

import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.models.User

interface PetConsumable {
    fun consume(user: User, pet: Pet)
}
