package com.solution.pointapi.web.v1.rewards.point.dto

import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint

class RewardPointResponse(
	private val rewardPoint: Map<String, Any?>
) {
	fun getData() = rewardPoint

	companion object {
		fun of(rewardPoint: RewardPoint): RewardPointResponse {
			return RewardPointResponse(
				mutableMapOf(
					"id" to rewardPoint.pointId,
					"memberId" to rewardPoint.memberPoint.memberId,
					"point" to rewardPoint.point,
					"createdAt" to rewardPoint.createdAt,
					"updatedAt" to rewardPoint.updatedAt,
					"delFlg" to rewardPoint.delFlg
				)
			)
		}
	}
}