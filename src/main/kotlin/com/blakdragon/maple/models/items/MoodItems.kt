package com.blakdragon.maple.models.items

import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.models.WellbeingStat

class MoodItems {

    companion object {
        val playground = WellbeingItem (
            id = "wellbeingItem.playground",
            displayName = "Playground",
            description = "You have no idea how you can fit this in your inventory, but maybe you shouldn't ask too many questions.",
            wellbeingStat = WellbeingStat.Mood,
            effect = 5
        )

        val ball = WellbeingItem(
            id = "wellbeingItem.ball",
            displayName = "Ball",
            description = "You want this to be thrown, but also, you don't want anyone to take it from you.",
            wellbeingStat = WellbeingStat.Mood,
            effect = 1
        )

        fun getAll(): List<WellbeingItem> {
            return listOf(
                playground,
                ball
            )
        }
    }
}
