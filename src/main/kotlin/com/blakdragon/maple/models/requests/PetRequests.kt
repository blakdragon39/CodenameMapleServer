package com.blakdragon.maple.models.requests

data class CreatePetRequest(
    val name: String,
    val species: String,
)

data class UseItemRequest(
    val itemId: String,
    val petId: String
)
