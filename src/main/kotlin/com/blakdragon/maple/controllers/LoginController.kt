package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.requests.LoginRequest
import com.blakdragon.maple.models.requests.UserLoginResponse
import com.blakdragon.maple.services.UserService
import com.blakdragon.maple.utils.EXPIRATION_PERIOD
import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("api/login")
class LoginController(private val userService: UserService) {

    @PostMapping
    fun login(@RequestBody request: LoginRequest): UserLoginResponse {
        val incorrectResponse = ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect email or password")
        val user = userService.getByEmail(request.email) ?: throw incorrectResponse

        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            throw incorrectResponse
        }

        user.token = UUID.randomUUID().toString()
        user.tokenExpiry = System.currentTimeMillis() + EXPIRATION_PERIOD

        userService.update(user)

        return UserLoginResponse(user)
    }
}
