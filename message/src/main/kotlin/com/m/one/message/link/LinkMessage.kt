package com.m.one.message.link


data class LinkMessage(
    var id: String,
    var title: String,
    var link: String,
    var createdAt: Long,
    var updatedAt: Long,
) {
}