package com.blakdragon.maple

import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.controllers.UserPetsController
import com.blakdragon.maple.models.*
import com.blakdragon.maple.models.requests.CreatePetRequest
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.utils.UsersLoggedInTests
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserPetCreationTests : UsersLoggedInTests() {

    @Autowired private lateinit var petController: PetController
    @Autowired private lateinit var userPetsController: UserPetsController

    @Autowired private lateinit var petDAO: PetDAO

    @AfterEach
    fun afterEach() {
        petDAO.deleteAll()
    }

    @Test
    fun noPets() {
        assertEquals(0, petController.getAll().size)
    }

    @Test
    fun createPets() {
        val catResponse = userPetsController.createPet(odin.token, odin.id, CreatePetRequest("", PetSpecies.Cat.toString()))

        assertEquals(catResponse.species, PetSpecies.Cat)
        assertEquals(1, petController.getAll().size)

        val dragonResponse = userPetsController.createPet(odin.token, odin.id, CreatePetRequest("", PetSpecies.Dragon.toString()))

        assertEquals(dragonResponse.species, PetSpecies.Dragon)
        assertEquals(2, petController.getAll().size)
    }

    @Test
    fun createPetWrongUserId() {
        try {
            userPetsController.createPet(odin.token, "Wrong ID", CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongAuth() {
        try {
            userPetsController.createPet("Wrong Token", odin.id, CreatePetRequest("", PetSpecies.Cow.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongUser() {
        try {
            userPetsController.createPet(odin.token, freya.id, CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun getPetsByUser() {
        userPetsController.createPet(odin.token, odin.id, CreatePetRequest("", PetSpecies.Rabbit.toString()))
        userPetsController.createPet(odin.token, odin.id, CreatePetRequest("", PetSpecies.Horse.toString()))

        userPetsController.createPet(freya.token, freya.id, CreatePetRequest("", PetSpecies.Cat.toString()))

        val firstUserPets = userPetsController.getPets(odin.id)
        assertEquals(2, firstUserPets.size)

        val secondUserPets = userPetsController.getPets(freya.id)
        assertEquals(1, secondUserPets.size)
    }

    @Test
    fun getPetsNoUser() {
        assertEquals(0, userPetsController.getPets("No ID").size)
    }

    @Test
    fun invalidSpecies() {
        try {
            userPetsController.createPet(odin.token, odin.id, CreatePetRequest("", "Invalid Pet Species"))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }
}
