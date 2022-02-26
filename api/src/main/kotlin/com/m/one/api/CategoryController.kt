package com.m.one.api

import com.m.one.api.CategoryController.Companion.BASE_URL
import com.m.one.domain.service.CategoryService
import com.m.one.message.category.CategoryMessage
import com.m.one.message.category.CategoryRequest
import mu.KLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(BASE_URL)
class CategoryController(
    private val categoryService: CategoryService
) {

    companion object: KLogging() {
        const val BASE_URL = "categorys"
        const val HEADER_USER = "H-USER-ID"
    }

    @PostMapping
    fun create(
        @RequestHeader(HEADER_USER) userId: String,
        @RequestBody categoryRequest: CategoryRequest
    ): Mono<CategoryMessage> {
        return categoryService.insert(userId, categoryRequest.category)
    }

    @GetMapping
    fun getCategoryByUserId(
        @RequestHeader(HEADER_USER) userId: String,
    ): Mono<List<CategoryMessage>> {
        return categoryService.findByUserId(userId)
    }

    @GetMapping("/{id}")
    fun getCategoryById(
        @PathVariable("id") id: String
    ): Mono<CategoryMessage> {
        return categoryService.findById(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody categoryRequest: CategoryRequest
    ): Mono<CategoryMessage> {
        return categoryService.update(id, categoryRequest.category)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: String,
    ): Mono<CategoryMessage> {
        return categoryService.delete(id)
    }

}