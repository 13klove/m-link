package com.m.one.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("link")
class Link(
    @Id
    var id: String? = null,
    var userId: String,
    var categoryId: String? = null,
    var title: String,
    var link: String,
    var createdAt: Long = Instant.now().toEpochMilli(),
    var updatedAt: Long = Instant.now().toEpochMilli(),
    var deletedAt: Long? = null
) {

    companion object {
        fun create(userId: String, categoryId: String?, title: String, link: String): Link {
            return Link(userId = userId, categoryId = categoryId, title = title, link = link)
        }
    }

    fun deleteCategoryId() {
        this.categoryId = null
    }

    fun delete() {
        this.deletedAt = Instant.now().toEpochMilli()
    }

}