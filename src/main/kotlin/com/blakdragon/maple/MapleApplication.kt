package com.blakdragon.maple

import com.blakdragon.maple.models.Item
import com.blakdragon.maple.models.wellbeingItems
import org.apache.commons.logging.LogFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
@EnableSwagger2
class MapleApplication() : ApplicationRunner {

    companion object {
        val log = LogFactory.getLog(MapleApplication::class.java)
        lateinit var items: List<Item>
    }

    override fun run(args: ApplicationArguments) {
        //startup logic
        initItems()
        checkItemIdsUnique()
    }

    private fun initItems() {
        val items: MutableList<Item> = mutableListOf()
        items.addAll(wellbeingItems)

        MapleApplication.items = items
    }

    private fun checkItemIdsUnique() {
        items
            .groupBy { id -> id }
            .filter { idGroup -> idGroup.value.size > 1 }
            .forEach { idGroup -> throw Exception("Found duplicate key ${idGroup.key}") }
    }
}

fun main(args: Array<String>) {
    runApplication<MapleApplication>(*args)
}
