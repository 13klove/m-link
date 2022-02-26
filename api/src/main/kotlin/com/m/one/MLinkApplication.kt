package com.m.one

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MLinkApplication

fun main(args: Array<String>) {
    runApplication<MLinkApplication>(*args)
}
