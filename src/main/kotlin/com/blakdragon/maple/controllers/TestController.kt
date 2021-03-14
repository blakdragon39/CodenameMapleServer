package com.blakdragon.maple.controllers

import com.blakdragon.maple.models.TestModel
import com.blakdragon.maple.services.TestService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/test")
class TestController(private val testService: TestService) {

    @GetMapping
    fun getAll(pageable: Pageable): Page<TestModel> = testService.getAll(pageable)

    @PostMapping
    fun insert(@RequestBody test: TestModel): TestModel = testService.insert(test)
}
