package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.ItemRequest
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users/{userId}/items")
class UserItemsController(
    private val userService: UserService,
    private val itemService: ItemService
) {

    @GetMapping
    fun getItems(@RequestHeader("Authorization") userToken: String, @PathVariable userId: String): List<Item> {
        val user = userService.getByIdAndValidate(userId, userToken)
        return user.items.map { itemId -> itemService.items.first { it.id == itemId } }
    }

    @PostMapping
    fun addItem(@RequestHeader("Authorization") userToken: String, @RequestBody request: ItemRequest): List<Item> {
        val user = userService.getByIdAndValidate(request.userId, userToken)
        user.items.add(request.itemId) //todo validation that there's a reason to add this item
        userService.update(user)
        return user.items.map { itemId -> itemService.items.first { it.id == itemId } } //todo refactor this out
    }

    @PutMapping
    fun consumeItem(): List<Item> {
        return listOf() //todo
    }
}
