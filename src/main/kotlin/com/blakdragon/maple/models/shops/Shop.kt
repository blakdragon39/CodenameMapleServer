package com.blakdragon.maple.models.shops

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

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
