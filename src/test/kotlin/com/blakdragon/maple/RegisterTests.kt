package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.utils.TestUserLogins
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@SpringBootTest
class RegisterTests {

    @Autowired private lateinit var userController: UserController

    @Autowired private lateinit var userDAO: UserDAO

    @AfterEach
    fun afterEach() {
        userDAO.deleteAll()
    }

    @Test
    fun registerFirstUser() {
        assertTrue { userController.getAll().isEmpty() }

        userController.registerUser(TestUserLogins.odinRegisterRequest)

        assertEquals(1, userController.getAll().size)
    }

    @Test
    fun registerSameUser() {
        userController.registerUser(TestUserLogins.odinRegisterRequest)

        try {
            userController.registerUser(TestUserLogins.odinRegisterRequest)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
        }

        assertEquals(1, userController.getAll().size)
    }

    @Test
    fun registerMultipleUsers() {
        userController.registerUser(TestUserLogins.odinRegisterRequest)
        userController.registerUser(TestUserLogins.freyaRegisterRequest)
        assertEquals(2, userController.getAll().size)
    }
}
