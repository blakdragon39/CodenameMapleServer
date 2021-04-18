package com.blakdragon.maple.services

import com.blakdragon.maple.models.shops.Shop
import com.blakdragon.maple.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ShopService(private val shopDAO: ShopDAO) : BasicCrud<String, Shop> {

    override fun getAll(): List<Shop> = shopDAO.findAll()

    override fun getAll(pageable: Pageable): Page<Shop> = shopDAO.findAll(pageable)

    override fun getById(id: String): Shop? = shopDAO.findByIdOrNull(id)

    override fun insert(obj: Shop): Shop = shopDAO.insert(obj)

    override fun update(obj: Shop): Shop {
        return if (obj.id != null && shopDAO.existsById(obj.id)) {
            shopDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): Shop? {
        return shopDAO.findByIdOrNull(id)?.apply {
            shopDAO.delete(this)
        }
    }
}
