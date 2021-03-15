package com.blakdragon.maple.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class User(
    @Id val id: String? = null,
    val email: String,
    val passwordHash: String,
    val joinDate: LocalDate,
    val displayName: String,
)
