package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserPetsController
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
        assertEquals(petController.getAll().size, 0)
    }

    @Test
    fun createPets() {
        val catResponse = userPetsController.createPet(firstUser.token!!, firstUser.id!!, CreatePetRequest("", PetSpecies.Cat.toString()))

        assertEquals(catResponse.species, PetSpecies.Cat)
        assertEquals(petController.getAll().size, 1)

        val dragonResponse = userPetsController.createPet(firstUser.token!!, firstUser.id!!, CreatePetRequest("", PetSpecies.Dragon.toString()))

        assertEquals(dragonResponse.species, PetSpecies.Dragon)
        assertEquals(petController.getAll().size, 2)
    }

    @Test
    fun createPetWrongUserId() {
        try {
            userPetsController.createPet(firstUser.token!!, "Wrong ID", CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongAuth() {
        try {
            userPetsController.createPet("Wrong Token", firstUser.id!!, CreatePetRequest("", PetSpecies.Cow.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.UNAUTHORIZED)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun createPetWrongUser() {
        try {
            userPetsController.createPet(firstUser.token!!, secondUser.id!!, CreatePetRequest("", PetSpecies.Dog.toString()))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.UNAUTHORIZED)
            assertTrue { petController.getAll().isEmpty() }
        }
    }

    @Test
    fun getPetsByUser() {
        userPetsController.createPet(firstUser.token!!, firstUser.id!!, CreatePetRequest("", PetSpecies.Rabbit.toString()))
        userPetsController.createPet(firstUser.token!!, firstUser.id!!, CreatePetRequest("", PetSpecies.Horse.toString()))

        userPetsController.createPet(secondUser.token!!, secondUser.id!!, CreatePetRequest("", PetSpecies.Cat.toString()))

        val firstUserPets = userPetsController.getPets(firstUser.id!!)
        assertEquals(firstUserPets.size, 2)

        val secondUserPets = userPetsController.getPets(secondUser.id!!)
        assertEquals(secondUserPets.size, 1)
    }

    @Test
    fun getPetsNoUser() {
        assertEquals(userPetsController.getPets("No ID").size, 0)
    }

    @Test
    fun invalidSpecies() {
        try {
            userPetsController.createPet(firstUser.token!!, firstUser.id!!, CreatePetRequest("", "Invalid Pet Species"))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.BAD_REQUEST)
            assertTrue { petController.getAll().isEmpty() }
        }
    }
}
