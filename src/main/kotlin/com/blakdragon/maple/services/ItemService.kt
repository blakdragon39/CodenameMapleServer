package com.blakdragon.maple.services

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.wellbeingItems
import org.springframework.stereotype.Service

@Service
class ItemService {

    final val items: List<Item>

    init {
        val addItems: MutableList<Item> = mutableListOf()
        addItems.addAll(wellbeingItems)

        items = addItems
    }

    fun getItems(itemIds: List<String>): List<Item> {
        return itemIds.map { itemId -> items.first { it.id == itemId } }
    }
}
