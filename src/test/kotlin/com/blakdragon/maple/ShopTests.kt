package com.blakdragon.maple

import com.blakdragon.maple.controllers.LoginController
import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.controllers.shops.ShopController
import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.RegisterRequest
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.models.shops.BuyRequest
import com.blakdragon.maple.models.shops.shops
import com.blakdragon.maple.services.ShopDAO
import com.blakdragon.maple.services.UserDAO
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
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
class ShopTests {

    @Autowired private lateinit var shopController: ShopController
    @Autowired private lateinit var loginController: LoginController
    @Autowired private lateinit var userController: UserController
    @Autowired private lateinit var userItemsController: UserItemsController

    @Autowired private lateinit var shopDAO: ShopDAO
    @Autowired private lateinit var userDAO: UserDAO

    private lateinit var firstUser: UserLoginResponse

    @BeforeAll
    fun beforeAll() {
        userController.registerUser(firstUserRequest)
        firstUser = loginController.login(firstUserLogin)
    }

    @AfterAll
    fun afterAll() {
        shopDAO.deleteAll()
        userDAO.deleteAll()
    }

    @Test
    fun shopsExist() {
        shops.forEach { shop ->
            assertNotNull(shopController.getShop(shop.id))
        }
    }

    @Test
    fun addRandomItems() {
        shopController.addMoreItems(shops[0].id)
        val shop = shopController.getShop(shops[0].id)

        assertTrue { shop.items.isNotEmpty() }
    }


    @Test
    fun testBuying() {
        val initialShop = shopController.getShop(shops[0].id)
        val firstItem = initialShop.items.first()

        val updatedShop = shopController.buyItem(initialShop.id, firstUser.token, BuyRequest(firstUser.id, firstItem.id))
        val userItems = userItemsController.getItems(firstUser.token, firstUser.id)

        assertEquals(initialShop.items.size - 1, updatedShop.items.size)
        assertTrue { userItems.map { it.id }.contains(firstItem.id) }
    }
}
