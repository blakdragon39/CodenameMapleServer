package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.*
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
    fun hasItems() {
        //todo replace with other API tests?
        val user = userService.getById(firstUser.id)
        val item1 = MapleApplication.items[0]
        val item2 = MapleApplication.items[2]

        user?.items?.add(item1.id)
        userService.update(user!!)
        var response = userItemsController.getItems(user.token!!, user.id!!)
        assertEquals(1, response.size)
        assertTrue { response.contains(item1) }


        user.items.add(item2.id)
        userService.update(user)
        response = userItemsController.getItems(user.token!!,user.id!!)
        assertEquals(2, response.size)
        assertTrue { response.contains(item2) }

        user.items.add(item1.id)
        userService.update(user)
        response = userItemsController.getItems(user.token!!,user.id!!)
        assertEquals(3, response.size)
    }
}
