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

    @GetMapping("/current")
    fun getCurrentPet(@PathVariable userId: String): Pet? {
        val user = userService.getById(userId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return user.currentPetId?.let { petService.getById(it) }
    }

    @PostMapping("/current/{petId}")
    fun setCurrentPet(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable userId: String,
        @PathVariable petId: String
    ): Pet {
        val user = userService.getByIdAndValidate(userId, userToken)
        val pet = petService.getByIdAndValidate(petId, user)

        user.currentPetId = pet.id
        userService.update(user)

        return pet
    }
}
