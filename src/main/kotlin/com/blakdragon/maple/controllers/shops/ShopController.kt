package com.blakdragon.maple.controllers.shops

import com.blakdragon.maple.models.shops.Shop
import com.blakdragon.maple.services.ShopService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/shops/{shopId}")
class ShopController(private val shopService: ShopService) {

    @GetMapping
    fun getShop(@PathVariable shopId: String): Shop {
        return shopService.getById(shopId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}
