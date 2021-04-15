package com.blakdragon.maple.services

import com.blakdragon.maple.MapleApplication
import com.blakdragon.maple.models.User
import com.blakdragon.maple.utils.BasicCrud
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userDAO: UserDAO) : BasicCrud<String, User> {

    override fun getAll(): List<User> = userDAO.findAll()

    override fun getAll(pageable: Pageable): Page<User> = userDAO.findAll(pageable)

    override fun getById(id: String): User? = userDAO.findByIdOrNull(id)

    override fun insert(obj: User): User {
        try {
            return userDAO.insert(obj)
        } catch (e : DuplicateKeyException) {
            MapleApplication.log.info(e);
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "That email is already in use")
        }
    }

    override fun update(obj: User): User {
        return if (obj.id != null && userDAO.existsById(obj.id)) {
            userDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): User? {
        return userDAO.findByIdOrNull(id)?.apply {
            userDAO.delete(this)
        }
    }

    fun getByIdAndValidate(id: String, token: String): User {
        val user = getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (token != user.token) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pet creation not allowed")
        //todo token expiry
        return user
    }

    fun getByEmail(email: String): User? = userDAO.findByEmail(email)
}
