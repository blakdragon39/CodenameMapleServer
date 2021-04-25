package com.blakdragon.maple.utils

import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.models.PetKit
import com.blakdragon.maple.models.PetSpecies
import com.blakdragon.maple.models.Wellbeing
import com.blakdragon.maple.models.requests.CreatePetRequest
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.RegisterRequest


class TestUserLogins {

    companion object {
        val odinRegisterRequest = RegisterRequest(
            email = "odin@freljord.com",
            password = "wRDvujwf34E",
            displayName = "Odin"
        )

        val odinLoginRequest = LoginRequest(
            email = odinRegisterRequest.email,
            password = odinRegisterRequest.password
        )

        val freyaRegisterRequest = RegisterRequest(
            email = "freya@valhalla.ca",
            password = "B5ZjoFAFqt",
            displayName = "Freya"
        )

        val freyaLoginRequest = LoginRequest(
            email = freyaRegisterRequest.email,
            password = freyaRegisterRequest.password
        )
    }
}


class TestPets {
    companion object {

        val jupiter = Pet(
            id = "yZNdWmnvMWGRm5ud",
            userId = "userId", //todo pass in user ids?
            name = "Jupiter",
            species = PetSpecies.Dragon,
            kit = PetKit.Druid,
            wellbeing = Wellbeing(0, 0, 0)
        )

        val jupiterCreateRequest = CreatePetRequest(
            name = jupiter.name,
            species = jupiter.species.toString()
        )

        val venus = Pet(
            id = "rFH8s29rVNHTscKN",
            userId = "userId2",
            name = "Venus",
            species = PetSpecies.Cat,
            kit = PetKit.Fire,
            wellbeing = Wellbeing(0, 0, 0)
        )

        val venusCreateRequest = CreatePetRequest(
            name = venus.name,
            species = venus.species.toString()
        )

        val mercuryCreateRequest = CreatePetRequest(
            name = "Mercury",
            species = PetSpecies.Cat.toString()
        )
    }
}
