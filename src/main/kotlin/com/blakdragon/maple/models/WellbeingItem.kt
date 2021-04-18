package com.blakdragon.maple.models

class WellbeingItem (
    id: String,
    displayName: String,
    val wellbeingStat: WellbeingStat,
    val effect: Int
) : Item(id, displayName)

val wellbeingItems: List<WellbeingItem> = listOf(
    WellbeingItem(
        id = "wellbeingItem.toothbrush",
        displayName = "Toothbrush",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 1
    ),
    WellbeingItem(
        id = "wellbeingItem.shampoo",
        displayName = "Shampoo",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 2
    ),
    WellbeingItem(
        id = "wellbeingItem.apple",
        displayName = "Apple",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 1
    ),
    WellbeingItem(
        id = "wellbeingItem.steakDinner",
        displayName = "Steak Dinner",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 2
    ),
    WellbeingItem (
        id = "wellbeingItem.playground",
        displayName = "Playground",
        wellbeingStat = WellbeingStat.Mood,
        effect = 5
    ),
    WellbeingItem(
        id = "wellbeingItem.ball",
        displayName = "Ball",
        wellbeingStat = WellbeingStat.Mood,
        effect = 1
    )
)
