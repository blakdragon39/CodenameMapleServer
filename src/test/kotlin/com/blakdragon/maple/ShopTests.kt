package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.controllers.shops.ShopController
import com.blakdragon.maple.models.shops.BuyRequest
import com.blakdragon.maple.models.shops.shops
import com.blakdragon.maple.services.ShopDAO
import com.blakdragon.maple.utils.UsersLoggedInTests
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopTests : UsersLoggedInTests() {

    @Autowired private lateinit var shopController: ShopController
    @Autowired private lateinit var userItemsController: UserItemsController

    @Autowired private lateinit var shopDAO: ShopDAO

    @AfterAll
    override fun afterAll() {
        super.afterAll()
        shopDAO.deleteAll()
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
    fun buyItem() {
        val initialShop = shopController.getShop(shops[0].id)
        val firstItem = initialShop.items.first()

        val updatedShop = shopController.buyItem(initialShop.id, odin.token, BuyRequest(odin.id, firstItem.id))
        val userItems = userItemsController.getItems(odin.token, odin.id)

        assertEquals(initialShop.items.size - 1, updatedShop.items.size)
        assertTrue { userItems.map { it.id }.contains(firstItem.id) }
    }
}
