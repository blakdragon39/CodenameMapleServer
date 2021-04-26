package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserPetsController
import com.blakdragon.maple.models.*
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.services.UserService
import com.blakdragon.maple.utils.TestPets
import com.blakdragon.maple.utils.UsersLoggedInTests
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetCurrentPetTests : UsersLoggedInTests() {

    @Autowired private lateinit var userPetsController: UserPetsController

    @Autowired private lateinit var petDAO: PetDAO
    @Autowired private lateinit var userDAO: UserDAO
    @Autowired private lateinit var userService: UserService

    //todo move pets to super class
    private lateinit var jupiter: Pet
    private lateinit var venus: Pet

    private lateinit var mercury: Pet

    @BeforeAll
    override fun beforeAll() {
        super.beforeAll()
        jupiter = userPetsController.createPet(odin.token, odin.id, TestPets.jupiterCreateRequest)
        venus = userPetsController.createPet(odin.token, odin.id, TestPets.venusCreateRequest)

        mercury = userPetsController.createPet(freya.token, freya.id, TestPets.mercuryCreateRequest)
    }

    @AfterAll
    override fun afterAll() {
        super.afterAll()
        petDAO.deleteAll()
    }

    @BeforeEach
    fun beforeEach() {
        val first = userService.getById(odin.id)
        first?.currentPetId = null
        userService.update(first!!)

        val second = userService.getById(freya.id)
        second?.currentPetId = null
        userService.update(second!!)
    }

    @Test
    fun noCurrentPet() {
        val firstResponse = userPetsController.getCurrentPet(odin.id)
        assertNull(firstResponse)

        val secondResponse = userPetsController.getCurrentPet(freya.id)
        assertNull(secondResponse)
    }

    @Test
    fun setCurrentPet() {
        userPetsController.setCurrentPet(odin.token, odin.id, jupiter.id!!)
        var responsePet = userPetsController.getCurrentPet(odin.id)

        assertNotNull(responsePet)
        assertEquals(jupiter.id, responsePet.id)

        userPetsController.setCurrentPet(odin.token, odin.id, venus.id!!)
        responsePet = userPetsController.getCurrentPet(odin.id)

        assertNotNull(responsePet)
        assertEquals(venus.id, responsePet.id)

        responsePet = userPetsController.getCurrentPet(freya.id)
        assertNull(responsePet)
    }

    @Test
    fun setCurrentPetWrongAuth() {
        try {
            userPetsController.setCurrentPet(odin.token, freya.id, mercury.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }

    @Test
    fun setCurrentPetWrongUser() {
        try {
            userPetsController.setCurrentPet(odin.token, odin.id, mercury.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }
}
