package com.blakdragon.maple.models

class WellbeingItem (
    id: String,
    displayName: String,
    description: String,
    val wellbeingStat: WellbeingStat,
    val effect: Int
) : Item(id = id, description = description, displayName = displayName)

val wellbeingItems: List<WellbeingItem> = listOf(
    WellbeingItem(
        id = "wellbeingItem.toothbrush",
        displayName = "Toothbrush",
        description = "You should use this twice a day according to 9/10 dentists, but I want to know what the last dentist knows.",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 1
    ),
    WellbeingItem(
        id = "wellbeingItem.shampoo",
        displayName = "Shampoo",
        description = "Use this to shine your coat right up!",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 2
    ),
    WellbeingItem(
        id = "wellbeingItem.apple",
        displayName = "Apple",
        description = "Bright, red, shiny, delicious, and good for you!",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 1
    ),
    WellbeingItem(
        id = "wellbeingItem.steakDinner",
        displayName = "Steak Dinner",
        description = "Extravagent and delicious.",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 2
    ),
    WellbeingItem (
        id = "wellbeingItem.playground",
        displayName = "Playground",
        description = "You have no idea how you can fit this in your inventory, but maybe you shouldn't ask too many questions.",
        wellbeingStat = WellbeingStat.Mood,
        effect = 5
    ),
    WellbeingItem(
        id = "wellbeingItem.ball",
        displayName = "Ball",
        description = "You want this to be thrown, but also, you don't want anyone to take it from you.",
        wellbeingStat = WellbeingStat.Mood,
        effect = 1
    )
)
