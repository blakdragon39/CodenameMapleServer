package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.LoginRequest
import com.blakdragon.maple.models.RegisterRequest
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals

private val firstUserLogin: LoginRequest = LoginRequest(
    email = "1",
    password = "1"
)

private val firstUserRegistration: RegisterRequest = RegisterRequest(
    email = firstUserLogin.email,
    password = firstUserLogin.password,
    displayName = "1"
)

private val secondUserLogin: LoginRequest = LoginRequest(
    email = "2",
    password = "2"
)

private val secondUserRegistration: RegisterRequest = RegisterRequest(
    email = firstUserLogin.email,
    password = firstUserLogin.password,
    displayName = "2"
)

@SpringBootTest
class LoginTests {

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var loginController: LoginController

    @Autowired
    private lateinit var userDAO: UserDAO

    @AfterEach
    fun afterEach() {
        userDAO.deleteAll()
    }

    @Test
    fun loginNoUsers() {
        try {
            loginController.login(firstUserLogin)
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun loginCorrectPassword() {
        val registerResponse = userController.registerUser(firstUserRegistration)
        val loginResponse = loginController.login(firstUserLogin)

        assertEquals(registerResponse.id, loginResponse.id)
    }

    @Test
    fun loginIncorrectPassword() {
        userController.registerUser(firstUserRegistration)

        try {
            loginController.login(LoginRequest(
                email = firstUserLogin.email,
                password = "Incorrect Password"
            ))
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
        }
    }
}
