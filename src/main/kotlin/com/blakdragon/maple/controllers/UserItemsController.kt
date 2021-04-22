package com.blakdragon.maple.controllers

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.Item
import com.blakdragon.maple.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users/{userId}/items")
class UserItemsController(
    private val userService: UserService
) {

    @GetMapping
    fun getItems(@RequestHeader("Authorization") userToken: String, @PathVariable userId: String): List<Item> {
        val user = userService.getByIdAndValidate(userId, userToken)
        return user.items.map { itemId -> MapleApplication.items.first { it.id == itemId } }
    }
}
