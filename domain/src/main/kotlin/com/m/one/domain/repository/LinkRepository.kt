package com.m.one.domain.repository

import com.m.one.domain.model.Link
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface LinkRepository: ReactiveMongoRepository<Link, String> {

    fun findByCategoryId(categoryId: String): Flux<Link>

    fun findByCategoryIdAndDeletedAtIsNull(categoryId: String): Flux<Link>

    fun findByUserIdAndDeletedAtIsNull(userId: String): Flux<Link>

    fun findByUserIdAndDeletedAtIsNullAndCategoryIdIsNull(userId: String): Flux<Link>

    fun findByUserIdAndDeletedAtIsNullAndCategoryIdIsNotNull(userId: String): Flux<Link>

}