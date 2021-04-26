package com.blakdragon.maple

import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.controllers.UserItemsController
import com.blakdragon.maple.models.items.HungerItems
import com.blakdragon.maple.models.items.HygieneItems
import com.blakdragon.maple.models.items.MoodItems
import com.blakdragon.maple.models.requests.UseItemRequest
import com.blakdragon.maple.utils.UsersLoggedInWithPetsTests
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UseItemTests : UsersLoggedInWithPetsTests() {

    @Autowired private lateinit var userItemsController: UserItemsController
    @Autowired private lateinit var petController: PetController

    //todo test caps, once they exist

    @Test
    fun userDoesntHaveItem() {
        try {
            userItemsController.useItem(odin.token, odin.id, UseItemRequest(MoodItems.ball.id, odinsPets.jupiter.id!!))
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.FORBIDDEN, e.status)
        }
    }

    @Test
    fun testHygiene() {
        val hygiene = odinsPets.jupiter.wellbeing.hygiene
        val item = HygieneItems.shampoo

        userItemsController.addItem(odin.token, odin.id, item.id)
        userItemsController.useItem(odin.token, odin.id, UseItemRequest(item.id, odinsPets.jupiter.id!!))

        val pet = petController.get(odinsPets.jupiter.id!!)
        assertEquals(hygiene + item.effect, pet.wellbeing.hygiene)
    }

    @Test
    fun testHunger() {
        val hunger = odinsPets.venus.wellbeing.hunger
        val item = HungerItems.apple

        userItemsController.addItem(odin.token, odin.id, item.id)
        userItemsController.useItem(odin.token, odin.id, UseItemRequest(item.id, odinsPets.venus.id!!))

        val pet = petController.get(odinsPets.venus.id!!)
        assertEquals(hunger + item.effect, pet.wellbeing.hunger)
    }

    @Test
    fun testMood() {
        val mood = odinsPets.jupiter.wellbeing.mood
        val item = MoodItems.ball

        userItemsController.addItem(odin.token, odin.id, item.id)
        userItemsController.useItem(odin.token, odin.id, UseItemRequest(item.id, odinsPets.jupiter.id!!))

        val pet = petController.get(odinsPets.jupiter.id!!)
        assertEquals(mood + item.effect, pet.wellbeing.mood)
    }
}
