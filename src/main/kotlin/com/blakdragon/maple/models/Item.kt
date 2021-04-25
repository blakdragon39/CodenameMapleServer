package com.blakdragon.maple.models

//todo description
abstract class Item (
    val id: String,
    val description: String,
    val displayName: String
) {

    override fun equals(other: Any?): Boolean {
        return (other as? Item)?.id == id
    }

    override fun hashCode() = id.hashCode()
}


class ItemRequest(
    val userId: String,
    val itemId: String
)
