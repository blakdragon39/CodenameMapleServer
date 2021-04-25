package com.blakdragon.maple.models.requests

import com.blakdragon.maple.models.User
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

open class UserResponse(user: User) {
    val id: String = user.id ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
    val displayName = user.displayName
}

class UserLoginResponse(user: User) : UserResponse(user) {
    val token: String = user.token  ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
    val tokenExpiry = user.tokenExpiry
}
