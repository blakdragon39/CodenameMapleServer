package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.User
import com.blakdragon.maple.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): List<User> = userService.getAll() //todo delete passwordHash from return values!

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): User? = userService.getById(id)

    @PostMapping
    fun insert(@RequestBody test: User): User = userService.insert(test)
}
