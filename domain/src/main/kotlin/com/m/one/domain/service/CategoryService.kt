package com.m.one.domain.service

import com.m.one.domain.model.Category
import com.m.one.domain.repository.CategoryRepository
import com.m.one.domain.repository.LinkRepository
import com.m.one.message.category.CategoryMessage
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.IllegalArgumentException

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val linkRepository: LinkRepository
) {

    companion object: KLogging() {
    }

    fun insert(userId: String, category: String): Mono<CategoryMessage> {
        return categoryRepository.save(
            Category.create(userId, category)
        ).map {
            CategoryMessage(it.id!!, it.category)
        }
    }

    fun findByUserId(userId: String): Mono<List<CategoryMessage>> {
        return categoryRepository.findByUserIdAndDeletedAtIsNotNull(userId)
            .map {
                CategoryMessage(it.id!!, it.category)
            }.collectList()
    }

    fun findById(id: String): Mono<CategoryMessage> {
        return categoryRepository.findById(id)
            .map {
                CategoryMessage(it.id!!, it.category)
            }
    }

    fun update(id: String, category: String): Mono<CategoryMessage> {
        return categoryRepository.findById(id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("no error")))
            .flatMap {
                it.category = category
                categoryRepository.save(it)
                    .map { updateEntity ->
                        CategoryMessage(updateEntity.id!!, updateEntity.category)
                    }
            }
    }

    @Transactional
    fun delete(id: String): Mono<CategoryMessage> {
        val category = categoryRepository.findById(id)
            .map {
                it.delete()
                categoryRepository.save(it)
                    .subscribe()
                CategoryMessage(it.id!!, it.category)
            }

        linkRepository.saveAll(
            linkRepository.findByCategoryId(id)
                .map {
                    it.deleteCategoryId()
                    it
                }
        )

        return category
    }

}