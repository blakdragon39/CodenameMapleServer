package com.blakdragon.maple

import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.services.ItemService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UseItemTests {

    @Autowired private lateinit var itemService: ItemService

    //todo test caps, once they exist
    //todo test use hunger item
    //todo test use hygiene item
    //todo test use mood item

    @Test
    fun testHygiene() {
//        val toothbrush = itemService.getItem()
    }
}
