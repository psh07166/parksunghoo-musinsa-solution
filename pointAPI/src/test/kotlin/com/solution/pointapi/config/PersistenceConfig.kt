package com.solution.pointapi.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@TestConfiguration
class PersistenceConfig(
	@PersistenceContext private val entityManager: EntityManager
) {

	@Bean
	fun jpaQueryFactory(): JPAQueryFactory {
		return JPAQueryFactory(entityManager)
	}
}