package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.CreatePetRequest
import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.models.PetKit
import com.blakdragon.maple.models.PetSpecies
import com.blakdragon.maple.services.PetService
import com.blakdragon.maple.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.Throws

@RestController
@RequestMapping("api/pets")
class PetController(
    private val petService: PetService,
    private val userService: UserService
) {

    @GetMapping
    fun getAll(): List<Pet> = petService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Pet? = petService.getById(id)

    @PostMapping
    @Throws(ResponseStatusException::class)
    fun createPet(@RequestHeader("Authorization") userToken: String, @RequestBody request: CreatePetRequest): Pet {
        val user = userService.getById(request.userId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (userToken != user.token) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Pet creation not allowed")
        val species = PetSpecies.fromString(request.species) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid species")

        return petService.insert(Pet(
            userId = user.id!!,
            name = request.name,
            species = species,
            kit = PetKit.Base
        ))
    }
}
