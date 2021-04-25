package com.blakdragon.maple

import com.blakdragon.maple.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemTests {

    @Autowired private lateinit var itemService: ItemService

    //todo test ItemService
}
