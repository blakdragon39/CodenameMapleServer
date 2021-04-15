package com.blakdragon.maple.models

//todo description
enum class WellbeingItem (
    val id: String,
    val displayName: String,
    val wellbeingStat: WellbeingStat,
    val effect: Int
) {
    Toothbrush(
        id = "wellbeingItem.toothbrush",
        displayName = "Toothbrush",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 1
    ),
    Shampoo(
        id = "wellbeingItem.shampoo",
        displayName = "Shampoo",
        wellbeingStat = WellbeingStat.Hygiene,
        effect = 2
    ),
    Apple(
        id = "wellbeingItem.apple",
        displayName = "Sandwich",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 1
    ),
    SteakDinner(
        id = "wellbeingItem.steakDinner",
        displayName = "Apple",
        wellbeingStat = WellbeingStat.Hunger,
        effect = 2
    ),
    Playground (
        id = "wellbeingItem.playground",
        displayName = "Playground",
        wellbeingStat = WellbeingStat.Mood,
        effect = 5
    ),
    Ball(
        id = "wellbeingItem.ball",
        displayName = "Ball",
        wellbeingStat = WellbeingStat.Mood,
        effect = 1
    );
}


