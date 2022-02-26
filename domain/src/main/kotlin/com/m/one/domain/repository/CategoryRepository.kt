package com.m.one.domain.repository

import com.m.one.domain.model.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CategoryRepository: ReactiveMongoRepository<Category, String> {

    fun findByUserIdAndDeletedAtIsNotNull(userId: String): Flux<Category>

}