package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.RegisterRequest
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

private val secondUserRequest: RegisterRequest = RegisterRequest(
    email = "2",
    password = "2",
    displayName = "2"
)

@SpringBootTest
class RegisterTests {

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var userDAO: UserDAO

    @AfterEach
    fun afterEach() {
        userDAO.deleteAll()
    }

    @Test
    fun registerFirstUser() {
        assertTrue { userController.getAll().isEmpty() }

        userController.registerUser(firstUserRequest)

        assertEquals(userController.getAll().size, 1)
    }

    @Test
    fun registerSameUser() {
        userController.registerUser(firstUserRequest)

        try {
            userController.registerUser(firstUserRequest)
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.BAD_REQUEST)
        }

        assertEquals(userController.getAll().size, 1)
    }

    @Test
    fun registerMultipleUsers() {
        userController.registerUser(firstUserRequest)
        userController.registerUser(secondUserRequest)
        assertEquals(userController.getAll().size, 2)
    }
}
