package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.RegisterRequest
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.services.UserDAO
import com.blakdragon.maple.services.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserItemsTests {

    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var loginController: LoginController
    @Autowired private lateinit var userItemsController: UserItemsController

    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var itemService: ItemService
    @Autowired private lateinit var userDAO: UserDAO

    private lateinit var firstUser: UserLoginResponse

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(firstUserRequest)
        firstUser = loginController.login(firstUserLogin)
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
    }

    @AfterEach
    fun afterEach() {
        //todo better integrated tests
        val user = userService.getById(firstUser.id)
        user?.items?.clear()
        userService.update(user!!)
    }

    @Test
    fun noItems() {
        val result = userItemsController.getItems(firstUser.token, firstUser.id)
        assertEquals(0, result.size)
    }

    @Test
    fun addItems() {
        val firstItem = itemService.items[0]
        val secondItem = itemService.items[1]

        val firstResponse = userItemsController.addItem(firstUser.token, firstUser.id, firstItem.id)
        assertEquals(1, firstResponse.size)
        assertTrue { firstResponse.contains(firstItem) }

        val secondResponse = userItemsController.addItem(firstUser.token, firstUser.id, secondItem.id)
        assertEquals(2, secondResponse.size)
        assertTrue { secondResponse.contains(secondItem) }
    }

    @Test
    fun getItems() {
        val firstItem = itemService.items[0]
        val secondItem = itemService.items[2]

        userItemsController.addItem(firstUser.token, firstUser.id, firstItem.id)
        var response = userItemsController.getItems(firstUser.token, firstUser.id)
        assertEquals(1, response.size)
        assertTrue { response.contains(firstItem) }

        userItemsController.addItem(firstUser.token, firstUser.id, secondItem.id)
        response = userItemsController.getItems(firstUser.token, firstUser.id)
        assertEquals(2, response.size)
        assertTrue { response.contains(secondItem) }

        userItemsController.addItem(firstUser.token, firstUser.id, firstItem.id)
        response = userItemsController.getItems(firstUser.token, firstUser.id)
        assertEquals(3, response.size)
    }
}
