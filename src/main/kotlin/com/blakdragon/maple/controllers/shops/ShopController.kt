package com.blakdragon.maple.controllers.shops

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.shops.BuyRequest
import com.blakdragon.maple.models.shops.ShopResponse
import com.blakdragon.maple.services.ItemService
import com.blakdragon.maple.services.ShopService
import com.blakdragon.maple.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/shops/{shopId}")
class ShopController(
    private val shopService: ShopService,
    private val userService: UserService,
    private val itemService: ItemService
) {

    @GetMapping
    fun getShop(@PathVariable shopId: String): ShopResponse {
        val shop = shopService.getByIdAndValidate(shopId)
        return ShopResponse(shop, itemService)
    }

    @PostMapping
    fun addMoreItems(@PathVariable shopId: String): ShopResponse {
        var shop = shopService.getByIdAndValidate(shopId)

        for (i in 1 .. 3) {
            val random = MapleApplication.random.nextInt(itemService.items.size)
            shop.items.add(itemService.items[random].id)
        }

        shop = shopService.update(shop)
        return ShopResponse(shop, itemService)
    }

    //todo handle multiple requests at once?
    @PutMapping
    fun buyItem(
        @PathVariable shopId: String,
        @RequestHeader("Authorization") userToken: String,
        @RequestBody buyRequest: BuyRequest): ShopResponse {

        var shop = shopService.getByIdAndValidate(shopId)
        val user = userService.getByIdAndValidate(buyRequest.userId, userToken)

        if (shop.items.contains(buyRequest.itemId)) {
            shop.items.remove(buyRequest.itemId)
            shop = shopService.update(shop)

            //todo money
            user.items.add(buyRequest.itemId)
            userService.update(user)

            return ShopResponse(shop, itemService)
        } else {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Shop doesn't have this item")
        }
    }
}
