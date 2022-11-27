package com.solution.pointapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class PointApiApplication

fun main(args: Array<String>) {
	runApplication<PointApiApplication>(*args)
}
