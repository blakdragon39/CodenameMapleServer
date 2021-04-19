package com.blakdragon.maple.controllers.shops

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.shops.ShopResponse
import com.blakdragon.maple.services.ShopService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/shops/{shopId}")
class ShopController(private val shopService: ShopService) {

    @GetMapping
    fun getShop(@PathVariable shopId: String): ShopResponse {
        val shop = shopService.getById(shopId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return ShopResponse(shop)
    }

    @PostMapping
    fun addMoreItems(@PathVariable shopId: String): ShopResponse {
        val shop = shopService.getById(shopId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        for (i in 1 .. 3) {
            val random = MapleApplication.random.nextInt(MapleApplication.items.size)
            shop.items.add(MapleApplication.items[random].id)
        }

        shopService.update(shop)

        return ShopResponse(shop)
    }
}
