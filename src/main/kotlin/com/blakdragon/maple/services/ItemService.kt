package com.blakdragon.maple.services

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.wellbeingItems
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ItemService {

    final val items: List<Item>

    init {
        val addItems: MutableList<Item> = mutableListOf()
        addItems.addAll(wellbeingItems)

        items = addItems
    }

    fun getItem(itemId: String): Item {
        try {
            return items.first { it.id == itemId }
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun getItems(itemIds: List<String>): List<Item> {
        return itemIds.map { getItem(it) }
    }
}
