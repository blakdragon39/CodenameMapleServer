package com.blakdragon.maple

import com.blakdragon.maple.controllers.PetController
import com.blakdragon.maple.services.PetDAO
import com.blakdragon.maple.services.PetService
import com.blakdragon.maple.utils.TestPets
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals


@SpringBootTest
class PetTests {

    @Autowired private lateinit var petController: PetController

    @Autowired private lateinit var petService: PetService
    @Autowired private lateinit var petDAO: PetDAO

    @BeforeEach
    fun beforeEach() {
        petDAO.deleteAll()
    }

    @AfterEach
    fun afterEach() {
        petDAO.deleteAll()
    }

    @Test
    fun getNoPets() {
        val result = petController.getAll()
        assertEquals(0, result.size)
    }

    @Test
    fun getNoPetById() {
        try {
            petController.get("Wrong ID")
        } catch (e: ResponseStatusException) {
            assertEquals(e.status, HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun getBydId() {
        petService.insert(TestPets.jupiter)

        val getPet = petController.get(TestPets.jupiter.id!!)
        assertEquals(TestPets.jupiter, getPet)
    }

    @Test
    fun getAll() {
        petService.insert(TestPets.jupiter)
        petService.insert(TestPets.venus)

        val result = petController.getAll()
        assertEquals(2, result.size)
    }
}
