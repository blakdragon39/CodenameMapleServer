package com.blakdragon.maple.models

import com.blakdragon.maple.models.requests.UserResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

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
