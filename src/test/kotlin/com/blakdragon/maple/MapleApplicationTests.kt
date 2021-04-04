package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.RegisterRequest
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertTrue

@SpringBootTest
class MapleApplicationTests {

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var userDAO: UserDAO

    @BeforeEach
    fun beforeEach() {
        userDAO.deleteAll()
    }

    @AfterEach
    fun afterEach() {
        userDAO.deleteAll()
    }


    @Test
    fun registerUser() {
        assertTrue { userController.getAll().isEmpty() }

        userController.registerUser(RegisterRequest(
            email = "",
            password = "",
            displayName = ""
        ))

        assertTrue { userController.getAll().size == 1 }
    }

    @Test
    fun passingTest() {
        assertTrue { userController.getAll().isEmpty() }
    }
}
