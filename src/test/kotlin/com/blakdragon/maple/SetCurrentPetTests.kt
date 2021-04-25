package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserPetsController
import com.blakdragon.maple.models.*
import com.blakdragon.maple.models.requests.CreatePetRequest
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.RegisterRequest
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.services.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private val firstUserRequest = RegisterRequest(
    email = "1",
    password = "1",
    displayName = "1"
)

private val firstUserLogin = LoginRequest(
    email = firstUserRequest.email,
    password = firstUserRequest.password
)

private val firstUserFirstPetRequest = CreatePetRequest(
    name = "1",
    species = PetSpecies.Dolphin.toString()
)

private val firstUserSecondPetRequest = CreatePetRequest(
    name = "2",
    species = PetSpecies.Rabbit.toString()
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

private val secondUserPetRequest = CreatePetRequest(
    name = "3",
    species = PetSpecies.Dragon.toString()
)

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetCurrentPetTests {

    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController
    @Autowired private lateinit var userPetsController: UserPetsController

    @Autowired private lateinit var petDAO: PetDAO
    @Autowired private lateinit var userDAO: UserDAO
    @Autowired private lateinit var userService: UserService

    private lateinit var firstUser: UserLoginResponse
    private lateinit var firstUserFirstPet: Pet
    private lateinit var firstUserSecondPet: Pet

    private lateinit var secondUser: UserLoginResponse
    private lateinit var secondUserPet: Pet

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(firstUserRequest)
        firstUser = loginController.login(firstUserLogin)
        firstUserFirstPet = userPetsController.createPet(firstUser.token, firstUser.id, firstUserFirstPetRequest)
        firstUserSecondPet = userPetsController.createPet(firstUser.token, firstUser.id, firstUserSecondPetRequest)

        userController.registerUser(secondUserRequest)
        secondUser = loginController.login(secondUserLogin)
        secondUserPet = userPetsController.createPet(secondUser.token, secondUser.id, secondUserPetRequest)
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
        petDAO.deleteAll()
    }

    @BeforeEach
    fun beforeEach() {
        val first = userService.getById(firstUser.id)
        first?.currentPetId = null
        userService.update(first!!)

        val second = userService.getById(secondUser.id)
        second?.currentPetId = null
        userService.update(second!!)
    }

    @Test
    fun noCurrentPet() {
        val firstResponse = userPetsController.getCurrentPet(firstUser.id)
        assertNull(firstResponse)

        val secondResponse = userPetsController.getCurrentPet(secondUser.id)
        assertNull(secondResponse)
    }

    @Test
    fun setCurrentPet() {
        userPetsController.setCurrentPet(firstUser.token, firstUser.id, firstUserFirstPet.id!!)
        var responsePet = userPetsController.getCurrentPet(firstUser.id)

        assertNotNull(responsePet)
        assertEquals(firstUserFirstPet.id, responsePet.id)

        userPetsController.setCurrentPet(firstUser.token, firstUser.id, firstUserSecondPet.id!!)
        responsePet = userPetsController.getCurrentPet(firstUser.id)

        assertNotNull(responsePet)
        assertEquals(firstUserSecondPet.id, responsePet.id)

        responsePet = userPetsController.getCurrentPet(secondUser.id)
        assertNull(responsePet)
    }

    @Test
    fun setCurrentPetWrongAuth() {
        try {
            userPetsController.setCurrentPet(firstUser.token, secondUser.id, secondUserPet.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }

    @Test
    fun setCurrentPetWrongUser() {
        try {
            userPetsController.setCurrentPet(firstUser.token, firstUser.id, secondUserPet.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }
}
