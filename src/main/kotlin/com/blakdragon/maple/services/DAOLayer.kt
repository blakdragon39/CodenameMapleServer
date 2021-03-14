package com.blakdragon.maple.services

import com.blakdragon.maple.models.TestModel
import org.springframework.data.mongodb.repository.MongoRepository

interface TestModelDAO : MongoRepository<TestModel, String>
