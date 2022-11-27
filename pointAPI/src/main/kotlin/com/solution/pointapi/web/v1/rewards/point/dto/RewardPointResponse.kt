package com.solution.pointapi.web.v1.rewards.point.dto

import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint

class RewardPointResponse(
	private val point: Map<String, Any?>
) {
	fun getData() = point

	companion object {
		fun of(point: RewardPoint): RewardPointResponse {
			return RewardPointResponse(
				mutableMapOf(
					"id" to point.pointId,
					"memberId" to point.memberPoint.memberId,
					"point" to point.point,
					"createdAt" to point.createdAt,
					"updatedAt" to point.updatedAt,
					"delFlg" to point.delFlg
				)
			)
		}
	}
}