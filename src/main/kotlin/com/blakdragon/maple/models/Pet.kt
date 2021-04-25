package com.blakdragon.maple.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Pet(
    @Id val id: String? = null,
    val userId: String,
    val name: String, //todo limit size, characters?
    val species: PetSpecies,
    val kit: PetKit,
    val wellbeing: Wellbeing
)

enum class PetSpecies {
    Cat,
    Dog,
    Rabbit,
    Cow,
    Horse,
    Dolphin,
    Dragon;

    companion object {
        fun fromString(speciesString: String): PetSpecies? {
            return values().firstOrNull { it.toString().equals(speciesString, ignoreCase = true) }
        }
    }
}

enum class PetKit {
    Base,
    Fire,
    Mermaid,
    Druid,
    Fruit,
    SabreTooth
}
