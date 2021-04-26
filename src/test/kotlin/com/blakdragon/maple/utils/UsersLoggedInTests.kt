package com.blakdragon.maple.utils

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UsersLoggedInTests {

    @Autowired protected lateinit var loginController: LoginController
    @Autowired protected lateinit var userController: UserController

    @Autowired private lateinit var userDAO: UserDAO

    protected lateinit var odin: UserLoginResponse
    protected lateinit var freya: UserLoginResponse

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(TestUserLogins.odinRegisterRequest)
        odin = loginController.login(TestUserLogins.odinLoginRequest)

        userController.registerUser(TestUserLogins.freyaRegisterRequest)
        freya = loginController.login(TestUserLogins.freyaLoginRequest)
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
    }
}
