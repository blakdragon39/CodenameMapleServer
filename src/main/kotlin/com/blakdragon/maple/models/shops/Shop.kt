package com.blakdragon.maple.models.shops

import org.springframework.data.annotation.Id

class Shop(
    @Id val id: String? = null,
    val name: String,
    val items: MutableList<String> = mutableListOf()
)

val shops: List<Shop> = listOf(
    Shop(
        id = "wellbeing",
        name = "The Wellbeing Shop"
    )
)
