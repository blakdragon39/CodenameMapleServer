package com.blakdragon.maple.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Document
data class User(
    @Id val id: String? = null,
    @Indexed(unique = true) val email: String,
    val passwordHash: String,
    val joinDate: Long,
    val displayName: String, //todo limit size, characters?

    var token: String? = null,
    var tokenExpiry: Long? = null,

    var currentPetId: String? = null,
    val items: MutableList<String> = mutableListOf()
) {
    fun toUserResponse() = UserResponse(this)
}

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
