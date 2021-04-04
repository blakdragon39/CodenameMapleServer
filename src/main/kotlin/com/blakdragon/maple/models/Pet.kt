package com.blakdragon.maple.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Pet(
    @Id val id: String? = null,
    val userId: String,
    val species: PetSpecies,
    val kit: PetKit
)

enum class PetSpecies {
    Cat,
    Dog,
    Rabbit,
    Cow,
    Horse,
    Dolphin,
    Dragon
}

enum class PetKit {
    Base,
    Fire,
    Mermaid,
    Druid,
    Fruit,
    SabreTooth
}

data class CreatePetRequest(
    val id: String,
    val species: PetSpecies,
)
