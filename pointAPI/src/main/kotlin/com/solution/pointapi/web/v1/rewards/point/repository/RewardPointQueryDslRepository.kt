package com.solution.pointapi.web.v1.rewards.point.repository

import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface RewardPointQueryDslRepository {
	fun findByUpdatedAt(pageable: Pageable, fromUpdateAt: LocalDateTime, toUpdateAt: LocalDateTime): Page<RewardPoint>
}