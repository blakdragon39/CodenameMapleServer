package com.blakdragon.maple.utils

import com.blakdragon.maple.controllers.UserPetsController
import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.services.PetDAO
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UsersLoggedInWithPetsTests : UsersLoggedInTests() {

    @Autowired protected lateinit var userPetsController: UserPetsController

    @Autowired private lateinit var petDAO: PetDAO

    protected lateinit var odinsPets: OdinsPets
    protected lateinit var freyasPets: FreyasPets

    @BeforeAll
    override fun beforeAll() {
        super.beforeAll()

        odinsPets = OdinsPets(
            userPetsController.createPet(odin.token, odin.id, TestPets.jupiterCreateRequest),
            userPetsController.createPet(odin.token, odin.id, TestPets.venusCreateRequest)
        )

        freyasPets = FreyasPets(
            userPetsController.createPet(freya.token, freya.id, TestPets.mercuryCreateRequest)
        )
    }

    @AfterAll
    override fun afterAll() {
        super.afterAll()
        petDAO.deleteAll()
    }

    inner class OdinsPets(
        val jupiter: Pet,
        val venus: Pet
    )

    inner class FreyasPets(
        val mercury: Pet
    )
}
