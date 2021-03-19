package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.RegisterRequest
import com.blakdragon.maple.models.User
import com.blakdragon.maple.models.UserResponse
import com.blakdragon.maple.services.UserService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): List<UserResponse> = userService.getAll().map { it.toUserResponse() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): UserResponse? = userService.getById(id)?.toUserResponse()

    @PostMapping
    fun registerUser(@RequestBody request: RegisterRequest): UserResponse {
        val passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt(12))
        val user = userService.insert(User(
            email = request.email,
            passwordHash = passwordHash,
            joinDate = System.currentTimeMillis(),
            displayName = request.displayName
        ))

        return UserResponse(user)
    }
}
