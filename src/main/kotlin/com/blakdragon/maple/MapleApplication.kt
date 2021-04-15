package com.blakdragon.maple

import com.blakdragon.maple.models.WellbeingItem
import com.blakdragon.maple.services.UserDAO
import org.apache.commons.logging.LogFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
@EnableSwagger2
class MapleApplication(private val userDAO: UserDAO) : ApplicationRunner {

    companion object {
        val log = LogFactory.getLog(MapleApplication::class.java)
    }

    override fun run(args: ApplicationArguments) {
        //startup logic
        checkItemIdsUnique()
    }

    private fun checkItemIdsUnique() {
        val itemIdsList: MutableList<String> = mutableListOf()
        itemIdsList.addAll(WellbeingItem.values().map { it.id })

        itemIdsList
            .groupBy { id -> id }
            .filter { idGroup -> idGroup.value.size > 1 }
            .forEach { idGroup -> throw Exception("Found duplicate key ${idGroup.key}") }
    }
}

fun main(args: Array<String>) {
    runApplication<MapleApplication>(*args)
}
