package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.utils.TestUserLogins
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals



@SpringBootTest
class LoginTests {

    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController

    @Autowired private lateinit var userDAO: UserDAO

    @AfterEach
    fun afterEach() {
        userDAO.deleteAll()
    }

    @Test
    fun loginNoUsers() {
        try {
            loginController.login(TestUserLogins.odinLoginRequest)
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun loginCorrectPassword() {
        val registerResponse = userController.registerUser(TestUserLogins.odinRegisterRequest)
        val loginResponse = loginController.login(TestUserLogins.odinLoginRequest)

        assertEquals(registerResponse.id, loginResponse.id)
    }

    @Test
    fun loginIncorrectPassword() {
        userController.registerUser(TestUserLogins.odinRegisterRequest)

        try {
            loginController.login(LoginRequest(
                email = TestUserLogins.odinLoginRequest.email,
                password = "Incorrect Password"
            ))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
        }
    }
}
