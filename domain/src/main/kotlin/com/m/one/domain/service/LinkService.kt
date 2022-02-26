package com.m.one.domain.service

import com.m.one.domain.model.Link
import com.m.one.domain.repository.LinkRepository
import com.m.one.message.link.LinkMessage
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LinkService(
    private val linkRepository: LinkRepository
) {

    fun insert(userId: String, categoryId: String?, title: String, link: String): Mono<LinkMessage> {
        return linkRepository.save(
            Link.create(userId, categoryId, title, link)
        ).map {
            LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
        }
    }

    fun findById(id: String): Mono<LinkMessage> {
        return linkRepository.findById(id)
            .map {
                LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
            }
    }

    fun findByCategoryId(categoryId: String): Mono<List<LinkMessage>> {
        return linkRepository.findByCategoryIdAndDeletedAtIsNull(categoryId)
            .map {
                LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
            }.collectList()
    }

    fun findByUserIdAndCategoryIdInclude(userId: String, categoryInclude: Boolean): Mono<List<LinkMessage>> {
        return if (categoryInclude) {
            linkRepository.findByUserIdAndDeletedAtIsNullAndCategoryIdIsNull(userId)
                .map {
                    LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
                }.collectList()
        } else {
            linkRepository.findByUserIdAndDeletedAtIsNullAndCategoryIdIsNotNull(userId)
                .map {
                    LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
                }.collectList()
        }
    }

    fun update(id: String, categoryId: String?, title: String, link: String): Mono<LinkMessage> {
        return linkRepository.findById(id)
            .flatMap {
                it.categoryId = categoryId
                it.title = title
                it.link = link
                linkRepository.save(it)
                    .map {
                        LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
                    }
            }
    }

    fun delete(id: String): Mono<LinkMessage> {
        return linkRepository.findById(id)
            .flatMap {
                it.deletedAt
                linkRepository.save(it)
                    .map {
                        LinkMessage(it.id!!, it.title, it.link, it.createdAt, it.updatedAt)
                    }
            }
    }

}