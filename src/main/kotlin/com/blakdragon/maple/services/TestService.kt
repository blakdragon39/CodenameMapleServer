package com.blakdragon.maple.services

import com.blakdragon.maple.models.TestModel
import com.blakdragon.maple.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class TestService(val testModelDAO: TestModelDAO) : BasicCrud<String, TestModel> {

    override fun getAll(pageable: Pageable): Page<TestModel> = testModelDAO.findAll(pageable)

    override fun getById(id: String): Optional<TestModel> = testModelDAO.findById(id)

    override fun insert(obj: TestModel): TestModel = testModelDAO.insert(obj)

    @Throws(ResponseStatusException::class)
    override fun update(obj: TestModel): TestModel {
        return if (obj.id != null && testModelDAO.existsById(obj.id)) {
            testModelDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): Optional<TestModel> {
        return testModelDAO.findById(id).apply {
            this.ifPresent { testModelDAO.delete(it) }
        }
    }
}
