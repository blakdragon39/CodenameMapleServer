package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.*
import com.blakdragon.maple.services.PetService
import com.blakdragon.maple.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.Throws

@RestController
@RequestMapping("api/users/{userId}/pets")
class UserPetsController(
    private val userService: UserService,
    private val petService: PetService
) {

    @GetMapping
    fun getPets(@PathVariable userId: String): List<Pet> = petService.getByUserId(userId)

    @PostMapping
    @Throws(ResponseStatusException::class)
    fun createPet(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable userId: String,
        @RequestBody request: CreatePetRequest
    ): Pet {
        val user = userService.getByIdAndValidate(userId, userToken)
        val species = PetSpecies.fromString(request.species) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid species")

        return petService.insert(Pet(
            userId = user.id!!,
            name = request.name,
            species = species,
            kit = PetKit.Base,
            wellbeing = Wellbeing()
        ))
    }

    @PostMapping("/{userId}/pets/{petId}/current")
    fun setCurrentPet(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable userId: String,
        @PathVariable petId: String
    ): UserResponse {
        val user = userService.getByIdAndValidate(userId, userToken)
        val pet = petService.getByIdAndValidate(petId, user)

        user.currentPetId = pet.id
        userService.update(user)

        return UserResponse(user)
    }
}
