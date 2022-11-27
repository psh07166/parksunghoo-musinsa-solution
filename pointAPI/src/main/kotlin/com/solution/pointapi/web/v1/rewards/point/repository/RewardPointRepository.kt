package com.solution.pointapi.web.v1.rewards.point.repository

import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import javax.persistence.LockModeType


interface RewardPointRepository: JpaRepository<RewardPoint, Int> {
	@Query("select p from RewardPoint p where p.pointId >= :id and p.delFlg = false ")
	fun findByPointId(id: Int): RewardPoint?

	@Query("select p from RewardPoint p join fetch p.memberPoint where p.updatedAt >= :fromUpdateAt and p.updatedAt <= :toUpdateAt and p.delFlg = false order by p.updatedAt asc ")
	fun findByUpdatedAt(fromUpdateAt: LocalDateTime, toUpdateAt: LocalDateTime): List<RewardPoint>

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from RewardPoint p where p.memberPoint.memberId = :id and p.updatedAt >= :fromUpdateAt and p.updatedAt <= :toUpdateAt and p.delFlg = false order by p.updatedAt asc ")
	fun findByMemberIdAndUpdatedAtForUpdate(id: Int, fromUpdateAt: LocalDateTime, toUpdateAt: LocalDateTime): RewardPoint?
}