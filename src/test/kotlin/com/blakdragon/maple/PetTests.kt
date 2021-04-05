package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.*
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val firstUserRequest: RegisterRequest = RegisterRequest(
    email = "1",
    password = "1",
    displayName = "1"
)

private val firstUserLogin: LoginRequest = LoginRequest(
    email = firstUserRequest.email,
    password = firstUserRequest.password
)

private val secondUserRequest: RegisterRequest = RegisterRequest(
    email = "2",
    password = "2",
    displayName = "2"
)

private val secondUserLogin: LoginRequest = LoginRequest(
    email = secondUserRequest.email,
    password = secondUserRequest.password
)

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetTests {

    @Autowired private lateinit var petController: PetController
    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController

    @Autowired private lateinit var petDAO: PetDAO
    @Autowired private lateinit var userDAO: UserDAO

    private lateinit var firstUser: UserLoginResponse
    private lateinit var secondUser: UserLoginResponse

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(firstUserRequest)
        firstUser = loginController.login(firstUserLogin)

        userController.registerUser(secondUserRequest)
        secondUser = loginController.login(secondUserLogin)
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
    }

    @AfterEach
    fun afterEach() {
        petDAO.deleteAll()
    }

    @Test
    fun noPets() {
        assertEquals(petController.getAll().size, 0)
    }

    @Test
    fun createPets() {
        val catResponse = petController.createPet(firstUser.token!!, CreatePetRequest(firstUser.id!!, PetSpecies.Cat))

        assertEquals(catResponse.species, PetSpecies.Cat)
        assertEquals(petController.getAll().size, 1)

        val dragonResponse = petController.createPet(firstUser.token!!, CreatePetRequest(firstUser.id!!, PetSpecies.Dragon))

        assertEquals(dragonResponse.species, PetSpecies.Dragon)
        assertEquals(petController.getAll().size, 2)
    }

    @Test
    fun createPetWrongUser() {
        try {
            petController.createPet(firstUser.token!!, CreatePetRequest("Wrong ID", PetSpecies.Dog))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongAuth() {
        try {
            petController.createPet("Wrong Token", CreatePetRequest(firstUser.id!!, PetSpecies.Cow))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.FORBIDDEN)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun getPetsByUser() {
        petController.createPet(firstUser.token!!, CreatePetRequest(firstUser.id!!, PetSpecies.Rabbit))
        petController.createPet(firstUser.token!!, CreatePetRequest(firstUser.id!!, PetSpecies.Horse))

        petController.createPet(secondUser.token!!, CreatePetRequest(secondUser.id!!, PetSpecies.Cat))

        val firstUserPets = userController.getPets(firstUser.id!!)
        assertEquals(firstUserPets.size, 2)

        val secondUserPets = userController.getPets(secondUser.id!!)
        assertEquals(secondUserPets.size, 1)
    }

    @Test
    fun getPetsNoUser() {
        assertEquals(userController.getPets("No ID").size, 0)
    }
}
