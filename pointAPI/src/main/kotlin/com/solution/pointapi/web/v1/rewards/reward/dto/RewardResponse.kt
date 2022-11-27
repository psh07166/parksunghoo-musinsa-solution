package com.solution.pointapi.web.v1.rewards.reward.dto

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward

class RewardResponse(
	private val reward: Map<String, Any?>
) {
	fun getData() = reward

	companion object {
		fun of(reward: Reward): RewardResponse {
			return RewardResponse(
				mutableMapOf(
					"id" to reward.rewardId,
					"rewardTypeName" to reward.rewardTypeName,
					"subject" to reward.subject,
					"content" to reward.content
				)
			)
		}
	}
}