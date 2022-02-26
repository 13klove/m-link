package com.m.one.api

import com.m.one.api.LinkController.Companion.BASE_URL
import com.m.one.domain.service.LinkService
import com.m.one.message.link.LinkMessage
import com.m.one.message.link.LinkRequest
import mu.KLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(BASE_URL)
class LinkController(
    private val linkService: LinkService
) {

    companion object: KLogging() {
        const val BASE_URL = "links"
        const val HEADER_USER = "H-USER-ID"
    }

    @PostMapping
    fun create(
        @RequestHeader(HEADER_USER) userId: String,
        @RequestBody linkRequest: LinkRequest
    ): Mono<LinkMessage> {
        with(linkRequest) {
            return linkService.insert(userId, categoryId, title, link)
        }
    }

    @GetMapping("/{id}")
    fun getLinkById(
        @PathVariable("id") id: String
    ): Mono<LinkMessage> {
        return linkService.findById(id)
    }

    @GetMapping("/category/{categoryId}")
    fun getLinkByCategoryId(
        @PathVariable("categoryId") categoryId: String
    ): Mono<List<LinkMessage>> {
        return linkService.findByCategoryId(categoryId)
    }

    @GetMapping("/user/{userId}")
    fun getLinkByUserId(
        @PathVariable("userId") userId: String,
        @RequestParam categoryInclude: Boolean
    ): Mono<List<LinkMessage>> {
        return linkService.findByUserIdAndCategoryIdInclude(userId, categoryInclude)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody linkRequest: LinkRequest
    ): Mono<LinkMessage> {
        with(linkRequest) {
            return linkService.update(id, categoryId, title, link)
        }
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: String,
    ): Mono<LinkMessage> {
        return linkService.delete(id)
    }

}