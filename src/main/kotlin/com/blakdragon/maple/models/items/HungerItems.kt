package com.blakdragon.maple.models.items

import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.models.WellbeingStat

class HungerItems {
    companion object {
        val apple = WellbeingItem(
            id = "wellbeingItem.apple",
            displayName = "Apple",
            description = "Bright, red, shiny, delicious, and good for you!",
            wellbeingStat = WellbeingStat.Hunger,
            effect = 1
        )

        val steakDinner = WellbeingItem(
            id = "wellbeingItem.steakDinner",
            displayName = "Steak Dinner",
            description = "Extravagent and delicious.",
            wellbeingStat = WellbeingStat.Hunger,
            effect = 2
        )

        fun getAll(): List<WellbeingItem> {
            return listOf(
                apple,
                steakDinner
            )
        }
    }
}
