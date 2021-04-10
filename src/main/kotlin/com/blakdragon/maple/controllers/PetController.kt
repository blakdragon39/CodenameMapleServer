package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.*
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
}
