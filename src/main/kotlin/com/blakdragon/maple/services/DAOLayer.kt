package com.blakdragon.maple.services

import com.blakdragon.maple.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDAO : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}
