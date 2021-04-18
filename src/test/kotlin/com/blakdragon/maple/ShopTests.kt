package com.blakdragon.maple

import com.blakdragon.maple.controllers.shops.ShopController
import com.blakdragon.maple.models.shops.shops
import com.blakdragon.maple.services.ShopDAO
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopTests {

    @Autowired private lateinit var shopController: ShopController

    @Autowired private lateinit var shopDAO: ShopDAO

    @AfterAll
    fun afterAll() {
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

        assertTrue { shop.items.size > 0 }
    }
}
