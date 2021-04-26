package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.items.HungerItems
import com.blakdragon.maple.models.items.HygieneItems
import com.blakdragon.maple.models.items.MoodItems
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.services.UserService
import com.blakdragon.maple.utils.TestUserLogins
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserItemsTests {

    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController
    @Autowired private lateinit var userItemsController: UserItemsController

    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var userDAO: UserDAO

    private lateinit var odin: UserLoginResponse

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(TestUserLogins.odinRegisterRequest)
        odin = loginController.login(TestUserLogins.odinLoginRequest)
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
    }

    @AfterEach
    fun afterEach() {
        //todo better integrated tests
        val user = userService.getById(odin.id)
        user?.items?.clear()
        userService.update(user!!)
    }

    @Test
    fun noItems() {
        val result = userItemsController.getItems(odin.token, odin.id)
        assertEquals(0, result.size)
    }

    @Test
    fun addItems() {
        val firstResponse = userItemsController.addItem(odin.token, odin.id, HungerItems.apple.id)
        assertEquals(1, firstResponse.size)
        assertTrue { firstResponse.contains(HungerItems.apple) }

        val secondResponse = userItemsController.addItem(odin.token, odin.id, HygieneItems.toothBrush.id)
        assertEquals(2, secondResponse.size)
        assertTrue { secondResponse.contains(HygieneItems.toothBrush) }
    }

    @Test
    fun getItems() {
        userItemsController.addItem(odin.token, odin.id, MoodItems.ball.id)
        var response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(1, response.size)
        assertTrue { response.contains(MoodItems.ball) }

        userItemsController.addItem(odin.token, odin.id, MoodItems.playground.id)
        response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(2, response.size)
        assertTrue { response.contains(MoodItems.playground) }

        userItemsController.addItem(odin.token, odin.id, MoodItems.ball.id)
        response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(3, response.size)
    }

    //todo test using an item
}
