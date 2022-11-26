package com.solution.pointapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableFeignClients
@RestController
class PointApiApplication{
	@GetMapping("/anyone-there")
	fun anyoneThere(request: HttpServletRequest) = ResponseEntity.ok("I'm here! API!!")
}

fun main(args: Array<String>) {
	runApplication<PointApiApplication>(*args)
}
