package com.blakdragon.maple.controllers

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.Item
import com.blakdragon.maple.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/users/{userId}/items")
class UserItemsController(
    val userService: UserService
) {

    @GetMapping
    fun getItems(@PathVariable userId: String): List<Item> {
        val user = userService.getById(userId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return user.items.map { itemId -> MapleApplication.items.first { it.id == itemId } }
    }
}
