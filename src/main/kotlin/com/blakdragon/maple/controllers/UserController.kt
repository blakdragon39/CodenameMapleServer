package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.User
import com.blakdragon.maple.models.requests.RegisterRequest
import com.blakdragon.maple.models.requests.UserResponse
import com.blakdragon.maple.services.UserService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): List<UserResponse> = userService.getAll().map { it.toUserResponse() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): UserResponse = userService.getById(id)?.toUserResponse() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    //todo email verification
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
