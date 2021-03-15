package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.User
import com.blakdragon.maple.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(pageable: Pageable): Page<User> = userService.getAll(pageable)

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Optional<User> = userService.getById(id)

    @PostMapping
    fun insert(@RequestBody test: User): User = userService.insert(test)
}
