package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.models.requests.UseItemRequest
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.services.PetService
import com.blakdragon.maple.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/users/{userId}/items")
class UserItemsController(
    private val userService: UserService,
    private val userPetService: PetService,
    private val itemService: ItemService
) {

    @GetMapping
    fun getItems(@RequestHeader("Authorization") userToken: String, @PathVariable userId: String): List<Item> {
        val user = userService.getByIdAndValidate(userId, userToken)
        return itemService.getItems(user.items)
    }

    @PostMapping("/{itemId}")
    fun addItem(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable userId: String,
        @PathVariable itemId: String
    ): List<Item> {
        val user = userService.getByIdAndValidate(userId, userToken)
        user.items.add(itemId) //todo validation that there's a reason to add this item
        userService.update(user)
        return itemService.getItems(user.items)
    }

    @PutMapping
    fun useItem(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable userId: String,
        @RequestBody request: UseItemRequest //todo other requests might not be for a pet
    ): List<Item> {
        val user = userService.getByIdAndValidate(userId, userToken)
        val pet = userPetService.getByIdAndValidate(request.petId, user)
        val item = itemService.getItem(request.itemId)
        if (!user.items.contains(request.itemId)) throw ResponseStatusException(HttpStatus.FORBIDDEN)

        when (item) {
            is WellbeingItem -> item.consume(user, pet)
        }

        user.items.remove(request.itemId)

        userPetService.update(pet)
        userService.update(user)

        return itemService.getItems(user.items)
    }
}
