package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.ItemService
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
    @Autowired private lateinit var itemService: ItemService
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
        val firstItem = itemService.items[0]
        val secondItem = itemService.items[1]

        val firstResponse = userItemsController.addItem(odin.token, odin.id, firstItem.id)
        assertEquals(1, firstResponse.size)
        assertTrue { firstResponse.contains(firstItem) }

        val secondResponse = userItemsController.addItem(odin.token, odin.id, secondItem.id)
        assertEquals(2, secondResponse.size)
        assertTrue { secondResponse.contains(secondItem) }
    }

    @Test
    fun getItems() {
        val firstItem = itemService.items[0]
        val secondItem = itemService.items[2]

        userItemsController.addItem(odin.token, odin.id, firstItem.id)
        var response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(1, response.size)
        assertTrue { response.contains(firstItem) }

        userItemsController.addItem(odin.token, odin.id, secondItem.id)
        response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(2, response.size)
        assertTrue { response.contains(secondItem) }

        userItemsController.addItem(odin.token, odin.id, firstItem.id)
        response = userItemsController.getItems(odin.token, odin.id)
        assertEquals(3, response.size)
    }

    //todo test using an item
}
