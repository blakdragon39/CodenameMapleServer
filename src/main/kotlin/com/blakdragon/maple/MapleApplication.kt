package com.blakdragon.maple

import com.blakdragon.maple.services.TestModelDAO
import org.apache.commons.logging.LogFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner


@SpringBootApplication
class MapleApplication(private val testModelDAO: TestModelDAO) : ApplicationRunner {

    private val log = LogFactory.getLog(MapleApplication::class.java)

    override fun run(args: ApplicationArguments) {
        //todo startup logic
    }
}

fun main(args: Array<String>) {
    runApplication<MapleApplication>(*args)
}
