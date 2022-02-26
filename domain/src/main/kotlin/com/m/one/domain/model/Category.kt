package com.m.one.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("category")
class Category(
    @Id
    var id: String? = null,
    var userId: String,
    var category: String,
    var createdAt: Long = Instant.now().toEpochMilli(),
    var updatedAt: Long = Instant.now().toEpochMilli(),
    var deletedAt: Long? = null
) {

    companion object {
        fun create(userId: String, category: String): Category {
            return Category(userId = userId, category = category)
        }
    }

    fun delete() {
        this.deletedAt = Instant.now().toEpochMilli()
    }

}