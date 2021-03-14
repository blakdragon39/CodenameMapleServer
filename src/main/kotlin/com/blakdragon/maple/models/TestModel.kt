package com.blakdragon.maple.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class TestModel(
    @Id val id: String? = null,
    val name: String,
)
