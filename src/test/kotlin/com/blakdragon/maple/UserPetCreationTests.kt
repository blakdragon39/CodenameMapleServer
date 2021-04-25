package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserPetsController
import com.blakdragon.maple.models.*
import com.blakdragon.maple.models.requests.CreatePetRequest
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.RegisterRequest
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val firstUserRequest = RegisterRequest(
    email = "1",
    password = "1",
    displayName = "1"
)

private val firstUserLogin = LoginRequest(
    email = firstUserRequest.email,
    password = firstUserRequest.password
)

private val secondUserRequest = RegisterRequest(
    email = "2",
    password = "2",
    displayName = "2"
)

private val secondUserLogin = LoginRequest(
    email = secondUserRequest.email,
    password = secondUserRequest.password
)

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserPetCreationTests {

    @Autowired private lateinit var petController: PetController
    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController
    @Autowired private lateinit var userPetsController: UserPetsController

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
        assertEquals(0, petController.getAll().size)
    }

    @Test
    fun createPets() {
        val catResponse = userPetsController.createPet(firstUser.token, firstUser.id, CreatePetRequest("", PetSpecies.Cat.toString()))

        assertEquals(catResponse.species, PetSpecies.Cat)
        assertEquals(1, petController.getAll().size)

        val dragonResponse = userPetsController.createPet(firstUser.token, firstUser.id, CreatePetRequest("", PetSpecies.Dragon.toString()))

        assertEquals(dragonResponse.species, PetSpecies.Dragon)
        assertEquals(2, petController.getAll().size)
    }

    @Test
    fun createPetWrongUserId() {
        try {
            userPetsController.createPet(firstUser.token, "Wrong ID", CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongAuth() {
        try {
            userPetsController.createPet("Wrong Token", firstUser.id, CreatePetRequest("", PetSpecies.Cow.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongUser() {
        try {
            userPetsController.createPet(firstUser.token, secondUser.id, CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun getPetsByUser() {
        userPetsController.createPet(firstUser.token, firstUser.id, CreatePetRequest("", PetSpecies.Rabbit.toString()))
        userPetsController.createPet(firstUser.token, firstUser.id, CreatePetRequest("", PetSpecies.Horse.toString()))

        userPetsController.createPet(secondUser.token, secondUser.id, CreatePetRequest("", PetSpecies.Cat.toString()))

        val firstUserPets = userPetsController.getPets(firstUser.id)
        assertEquals(2, firstUserPets.size)

        val secondUserPets = userPetsController.getPets(secondUser.id)
        assertEquals(1, secondUserPets.size)
    }

    @Test
    fun getPetsNoUser() {
        assertEquals(0, userPetsController.getPets("No ID").size)
    }

    @Test
    fun invalidSpecies() {
        try {
            userPetsController.createPet(firstUser.token, firstUser.id, CreatePetRequest("", "Invalid Pet Species"))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
            assertTrue { petController.getAll().isEmpty() }
        }
    }
}
