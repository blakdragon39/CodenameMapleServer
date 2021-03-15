package com.blakdragon.maple.services

import com.blakdragon.maple.models.User
import com.blakdragon.maple.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class UserService(val userDAO: UserDAO) : BasicCrud<String, User> {

    override fun getAll(pageable: Pageable): Page<User> = userDAO.findAll(pageable)

    override fun getById(id: String): Optional<User> = userDAO.findById(id)

    override fun insert(obj: User): User = userDAO.insert(obj)

    @Throws(ResponseStatusException::class)
    override fun update(obj: User): User {
        return if (obj.id != null && userDAO.existsById(obj.id)) {
            userDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): Optional<User> {
        return userDAO.findById(id).apply {
            this.ifPresent { userDAO.delete(it) }
        }
    }
}
