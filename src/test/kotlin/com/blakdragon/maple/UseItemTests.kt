package com.blakdragon.maple

import com.blakdragon.maple.controllers.UserController
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.utils.TestUserLogins
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UseItemTests {

    //todo move tests with login to abstract base class
    @Autowired private lateinit var userController: UserController

    @Autowired private lateinit var itemService: ItemService


    //todo test caps, once they exist
    //todo test use hunger item
    //todo test use hygiene item
    //todo test use mood item

    @BeforeAll
    fun beforeAll() {
//        userController.registerUser(TestUserLogins.odinRegisterRequest)
//        odin = loginController.login(TestUserLogins.odinLoginRequest)
    }

    @AfterAll
    fun afterAll() {
//        userDAO.deleteAll()
    }

    @Test
    fun testHygiene() {
//        val toothbrush = itemService.getItem()
    }

    @Test
    fun testHunger() {

    }

    @Test
    fun testMood() {

    }
}
