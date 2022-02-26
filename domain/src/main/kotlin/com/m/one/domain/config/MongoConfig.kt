package com.m.one.domain.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@Configuration
@EnableReactiveMongoRepositories(basePackages = ["com.m.one.domain.repository"])
class MongoConfig(
    @Value("\${spring.data.mongodb.uri}")
    val mongoUrl : String,
    @Value("\${spring.data.mongodb.database}")
    val database: String
): AbstractReactiveMongoConfiguration() {

    @Bean
    fun transactionManager(factory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager {
        return ReactiveMongoTransactionManager(factory)
    }

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create(mongoUrl)
    }

    override fun getDatabaseName(): String = database

}