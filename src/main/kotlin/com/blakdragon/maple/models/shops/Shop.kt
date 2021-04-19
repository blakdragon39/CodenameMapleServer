package com.blakdragon.maple.models.shops

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.Item
import com.blakdragon.maple.services.ShopService
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Document
class Shop(
    @Id val id: String,
    val name: String,
    val items: MutableList<String> = mutableListOf()
)

val shops: List<Shop> = listOf(
    Shop(
        id = "wellbeing",
        name = "The Wellbeing Shop"
    )
)

class ShopResponse(shop: Shop) {
    val id: String = shop.id
    val name: String = shop.name
    val items: List<Item> = shop.items.map { itemId ->
        MapleApplication.items.find { it.id == itemId } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}
