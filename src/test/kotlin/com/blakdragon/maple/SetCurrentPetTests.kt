package com.blakdragon.maple

import com.blakdragon.maple.services.UserService
import com.blakdragon.maple.utils.UsersLoggedInWithPetsTests
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetCurrentPetTests : UsersLoggedInWithPetsTests() {

    @Autowired private lateinit var userService: UserService

    @BeforeEach
    fun beforeEach() {
        val first = userService.getById(odin.id)
        first?.currentPetId = null
        userService.update(first!!)

        val second = userService.getById(freya.id)
        second?.currentPetId = null
        userService.update(second!!)
    }

    @Test
    fun noCurrentPet() {
        val firstResponse = userPetsController.getCurrentPet(odin.id)
        assertNull(firstResponse)

        val secondResponse = userPetsController.getCurrentPet(freya.id)
        assertNull(secondResponse)
    }

    @Test
    fun setCurrentPet() {
        userPetsController.setCurrentPet(odin.token, odin.id, odinsPets.jupiter.id!!)
        var responsePet = userPetsController.getCurrentPet(odin.id)

        assertNotNull(responsePet)
        assertEquals(odinsPets.jupiter.id, responsePet.id)

        userPetsController.setCurrentPet(odin.token, odin.id, odinsPets.venus.id!!)
        responsePet = userPetsController.getCurrentPet(odin.id)

        assertNotNull(responsePet)
        assertEquals(odinsPets.venus.id, responsePet.id)

        responsePet = userPetsController.getCurrentPet(freya.id)
        assertNull(responsePet)
    }

    @Test
    fun setCurrentPetWrongAuth() {
        try {
            userPetsController.setCurrentPet(odin.token, freya.id, freyasPets.mercury.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }

    @Test
    fun setCurrentPetWrongUser() {
        try {
            userPetsController.setCurrentPet(odin.token, odin.id, freyasPets.mercury.id!!)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.status)
        }
    }
}
