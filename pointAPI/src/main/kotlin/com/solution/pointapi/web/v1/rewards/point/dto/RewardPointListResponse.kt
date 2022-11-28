package com.solution.pointapi.web.v1.rewards.point.dto

import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import java.util.stream.Collectors

class RewardPointListResponse (
	private val rewardPoints: Map<String, Any>
) {
	fun getData() = rewardPoints

	companion object {
		fun of(rewardPoint: List<RewardPoint>) : RewardPointListResponse {

			val result: Map<String, Any> = mapOf(
				"content" to rewardPoint.stream().map {
					mapOf(

						"memberId" to it.memberPoint.memberId,
						"point" to it.point,
						"totalPoint" to it.memberPoint.point,
						"getCount" to it.memberPoint.getCount,
						"getDate" to it.updatedAt
					)
				}.collect(Collectors.toList())
			)
			return RewardPointListResponse(result)
		}
	}
}