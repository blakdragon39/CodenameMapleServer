package com.blakdragon.maple.services

import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.models.User
import com.blakdragon.maple.models.shops.Shop
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDAO : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}

interface PetDAO : MongoRepository<Pet, String> {
    fun findByUserId(userId: String): List<Pet>
}


interface ShopDAO : MongoRepository<Shop, String>
