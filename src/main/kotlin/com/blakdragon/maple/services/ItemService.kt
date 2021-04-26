package com.blakdragon.maple.services

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.items.HungerItems
import com.blakdragon.maple.models.items.HygieneItems
import com.blakdragon.maple.models.items.MoodItems
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ItemService {

    final val items: List<Item>

    init {
        // todo add all items with reflection?
        // https://github.com/ronmamo/reflections
        val addItems: MutableList<Item> = mutableListOf()
        addItems.addAll(HungerItems.getAll())
        addItems.addAll(HygieneItems.getAll())
        addItems.addAll(MoodItems.getAll())

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
