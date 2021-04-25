package com.blakdragon.maple

import com.blakdragon.maple.services.ItemService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import springfox.documentation.swagger2.annotations.EnableSwagger2
import kotlin.random.Random


@SpringBootApplication
@EnableSwagger2
class MapleApplication(val itemService: ItemService) : ApplicationRunner {

    companion object {
        val random = Random(System.currentTimeMillis())
    }

    //startup logic
    override fun run(args: ApplicationArguments) {
        checkItemIdsUnique()
    }


    private fun checkItemIdsUnique() {
        itemService.items
            .groupBy { id -> id }
            .filter { idGroup -> idGroup.value.size > 1 }
            .forEach { idGroup -> throw Exception("Found duplicate key ${idGroup.key}") }
    }
}

fun main(args: Array<String>) {
    runApplication<MapleApplication>(*args)
}
