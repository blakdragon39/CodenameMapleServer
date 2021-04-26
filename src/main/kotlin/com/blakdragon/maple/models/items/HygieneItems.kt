package com.blakdragon.maple.models.items

import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.models.WellbeingStat

class HygieneItems {
     companion object {
         val toothBrush = WellbeingItem(
             id = "wellbeingItem.toothbrush",
             displayName = "Toothbrush",
             description = "You should use this twice a day according to 9/10 dentists, but I want to know what the last dentist knows.",
             wellbeingStat = WellbeingStat.Hygiene,
             effect = 1
         )

         val shampoo = WellbeingItem(
             id = "wellbeingItem.shampoo",
             displayName = "Shampoo",
             description = "Use this to shine your coat right up!",
             wellbeingStat = WellbeingStat.Hygiene,
             effect = 2
         )

         fun getAll(): List<WellbeingItem> {
             return listOf(
                 toothBrush,
                 shampoo
             )
         }
     }
}
