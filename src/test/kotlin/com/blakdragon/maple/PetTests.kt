package com.blakdragon.maple

import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.models.PetKit
import com.blakdragon.maple.models.PetSpecies
import com.blakdragon.maple.models.Wellbeing
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.PetService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals

private val firstPet = Pet(
    id = "firstId",
    userId = "userId",
    name = "name",
    species = PetSpecies.Cat,
    kit = PetKit.Fire,
    wellbeing = Wellbeing(0, 0, 0)
)

private val secondPet = Pet(
    id = "secondId",
    userId = "userId2",
    name = "name2",
    species = PetSpecies.Cat,
    kit = PetKit.Fire,
    wellbeing = Wellbeing(0, 0, 0)
)

@SpringBootTest
class PetTests {

    @Autowired private lateinit var petController: PetController

    @Autowired private lateinit var petService: PetService
    @Autowired private lateinit var petDAO: PetDAO

    @BeforeEach
    fun beforeEach() {
        petDAO.deleteAll()
    }

    @Test
    fun getNoPets() {
        val result = petController.getAll()
        assertEquals(0, result.size)
    }

    @Test
    fun getNoPetById() {
        try {
            petController.get("Wrong ID")
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun getBydId() {
        petService.insert(firstPet)

        val getPet = petController.get(firstPet.id!!)
        assertEquals(firstPet, getPet)
    }

    @Test
    fun getAll() {
        petService.insert(firstPet)
        petService.insert(secondPet)

        val result = petController.getAll()
        assertEquals(2, result.size)
    }
}
