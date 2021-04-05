package com.blakdragon.maple.services

import com.blakdragon.maple.models.Pet
import com.blakdragon.maple.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PetService(private val petDAO: PetDAO) : BasicCrud<String, Pet> {

    override fun getAll(): List<Pet> = petDAO.findAll()

    override fun getAll(pageable: Pageable): Page<Pet> = petDAO.findAll(pageable)

    override fun getById(id: String): Pet? = petDAO.findByIdOrNull(id)

    override fun insert(obj: Pet): Pet = petDAO.insert(obj)

    @Throws(ResponseStatusException::class)
    override fun update(obj: Pet): Pet {
        return if (obj.id != null && petDAO.existsById(obj.id)) {
            petDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): Pet? {
        return petDAO.findByIdOrNull(id)?.apply {
            petDAO.delete(this)
        }
    }

    fun getByUserId(userId: String): List<Pet> = petDAO.findByUserId(userId)
}
