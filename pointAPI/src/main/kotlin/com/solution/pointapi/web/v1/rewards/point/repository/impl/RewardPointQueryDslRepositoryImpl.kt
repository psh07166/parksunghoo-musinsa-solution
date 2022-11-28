package com.solution.pointapi.web.v1.rewards.point.repository.impl

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.solution.pointapi.web.v1.member.entity.QMemberPoint
import com.solution.pointapi.web.v1.rewards.point.entity.QRewardPoint
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointQueryDslRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class RewardPointQueryDslRepositoryImpl(
	private val jpaQueryFactory: JPAQueryFactory
): RewardPointQueryDslRepository, QuerydslRepositorySupport(RewardPoint::class.java) {

	override fun findByUpdatedAt(pageable: Pageable, fromUpdateAt: LocalDateTime, toUpdateAt: LocalDateTime): Page<RewardPoint> {
		val qr = QRewardPoint.rewardPoint
		val qm = QMemberPoint.memberPoint


		val query = jpaQueryFactory.select(
			Projections.fields(
				qr,
				qr.pointId,
				qr.memberPoint.memberId,
				qr.point,
				qr.createdAt,
				qr.updatedAt
			)
		).from(qr)
			.leftJoin(qm).on(qr.memberPoint.eq(qm))

		val rewardPoints = querydsl?.applyPagination(pageable, query)?.fetchJoin()?.fetch()
		return PageImpl(rewardPoints ?: emptyList(), pageable, rewardPoints?.size?.toLong() ?: 0L)
	}
}