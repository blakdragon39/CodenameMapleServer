package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.items.MoodItems
import com.blakdragon.maple.models.requests.UseItemRequest
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.utils.UsersLoggedInWithPetsTests
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UseItemTests : UsersLoggedInWithPetsTests() {

    @Autowired private lateinit var userItemsController: UserItemsController

    @Autowired private lateinit var itemService: ItemService


    //todo test caps, once they exist
    //todo test use hunger item
    //todo test use hygiene item
    //todo test use mood item

    @Test
    fun userDoesntHaveItem() {
//        userItemsController.useItem(odin.token, odin.id, UseItemRequest(MoodItems.ball, ))
    }

//    @Test
//    fun testHygiene() {
//
//    }
//
//    @Test
//    fun testHunger() {
//
//    }
//
//    @Test
//    fun testMood() {
//
//    }
}
